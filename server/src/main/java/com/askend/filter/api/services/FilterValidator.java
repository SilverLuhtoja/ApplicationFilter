package com.askend.filter.api.services;

import com.askend.filter.api.models.Filter;
import com.askend.filter.api.models.enums.FilterCriteria;
import com.askend.filter.api.exceptions.CriteriaException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class FilterValidator {

  public void validate(Filter filter) {
    validateCriteriaName(filter.criteria());
    validateCriteriaOperator(filter.criteria(), filter.operator());
    validateCriteriaValue(filter.criteria(), filter.value());
  }

  private void validateCriteriaName(String criteria) {
    try {
      FilterCriteria.valueOf(criteria.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new CriteriaException(
          String.format("Invalid Filter criteria name: %s ", criteria)
      );
    }
  }

  private void validateCriteriaOperator(String criteria, String operator) {
    var crit = FilterCriteria.valueOf(criteria.toUpperCase());
    boolean operatorValid = switch (crit) {
      case AMOUNT -> Arrays.stream(FilterCriteria.CriteriaAmountOperators.values())
          .anyMatch(op -> op.name().equalsIgnoreCase(operator));
      case TITLE -> Arrays.stream(FilterCriteria.CriteriaTitleOperators.values())
          .anyMatch(op -> op.name().equalsIgnoreCase(operator));
      case DATE -> Arrays.stream(FilterCriteria.CriteriaDateOperators.values())
          .anyMatch(op -> op.name().equalsIgnoreCase(operator));
    };

    if (!operatorValid) {
      throw new CriteriaException(
          String.format("Invalid Filter operator: %s for criteria %s", operator, criteria)
      );
    }
  }

  private void validateCriteriaValue(String criteria, String value) {
    var crit = FilterCriteria.valueOf(criteria.toUpperCase());
    boolean valueValid = switch (crit) {
      case AMOUNT -> {
        try {
          Double.parseDouble(value);
          yield true;
        } catch (NumberFormatException e) {
          yield false;
        }
      }
      case TITLE -> value != null && !value.isBlank();
      case DATE -> {
        try {
          LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
          yield true;
        } catch (DateTimeParseException e) {
          yield false;
        }
      }
    };

    if (!valueValid) {
      throw new CriteriaException(
          String.format("Invalid Filter value: %s for criteria %s", value, criteria)
      );
    }
  }
}
