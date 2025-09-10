package com.askend.filter.api.services;

import static com.askend.filter.api.datasets.FilterDataset.AMOUNT_VALUE;
import static com.askend.filter.api.datasets.FilterDataset.DATE_VALUE;
import static com.askend.filter.api.datasets.FilterDataset.TITLE_VALUE;
import static com.askend.filter.api.models.enums.FilterCriteria.AMOUNT;
import static com.askend.filter.api.models.enums.FilterCriteria.CriteriaAmountOperators.GREATER_THAN;
import static com.askend.filter.api.models.enums.FilterCriteria.CriteriaDateOperators.BEFORE;
import static com.askend.filter.api.models.enums.FilterCriteria.CriteriaDateOperators.EQUALS;
import static com.askend.filter.api.models.enums.FilterCriteria.DATE;
import static com.askend.filter.api.models.enums.FilterCriteria.TITLE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.askend.filter.api.exceptions.CriteriaException;
import com.askend.filter.api.models.Filter;
import com.askend.filter.config.UnitTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;

class FilterValidatorTest extends UnitTest {

  @InjectMocks
  private FilterValidator validator;

  @Nested
  class ValidateCriteriaName {

    @ParameterizedTest
    @ValueSource(strings = {"AMOUNT", "TITLE", "DATE"})
    void shouldSuccessfullyValidate(String criteria) {
      Filter f = switch (criteria) {
        case "AMOUNT" -> new Filter(criteria, GREATER_THAN.name(), AMOUNT_VALUE);
        case "TITLE" -> new Filter(criteria, EQUALS.name(), TITLE_VALUE);
        case "DATE" -> new Filter(criteria, BEFORE.name(), DATE_VALUE);
        default -> throw new IllegalArgumentException();
      };
      assertDoesNotThrow(() -> validator.validate(f));
    }

    @Test
    void shouldThrowWhenCriteriaNameInvalid() {
      Filter f = new Filter("amounts", GREATER_THAN.name(), AMOUNT_VALUE);
      assertThrows(CriteriaException.class, () -> validator.validate(f));
    }
  }

  @Nested
  class ValidateCriteriaOperator {

    @ParameterizedTest
    @ValueSource(strings = {"AMOUNT", "TITLE", "DATE"})
    void shouldSuccessfullyValidate(String criteria) {
      Filter f = switch (criteria) {
        case "AMOUNT" -> new Filter(criteria, GREATER_THAN.name(), AMOUNT_VALUE);
        case "TITLE" -> new Filter(criteria, EQUALS.name(), TITLE_VALUE);
        case "DATE" -> new Filter(criteria, BEFORE.name(), DATE_VALUE);
        default -> throw new IllegalArgumentException();
      };
      assertDoesNotThrow(() -> validator.validate(f));
    }

    @ParameterizedTest
    @ValueSource(strings = {"100,1", "abc", "", "   ", "123abc"})
    void shouldThrowWhenAmountValueInvalid(String value) {
      Filter f = new Filter(AMOUNT.name(), GREATER_THAN.name(), value);
      assertThrows(CriteriaException.class, () -> validator.validate(f));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n"})
    void shouldThrowWhenTitleValueInvalid(String value) {
      Filter f = new Filter(TITLE.name(), EQUALS.name(), value);
      assertThrows(CriteriaException.class, () -> validator.validate(f));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2025/09/07", "07-09-2025", "20250907", "invalid-date"})
    void shouldThrowWhenDateValueInvalid(String value) {
      Filter f = new Filter(DATE.name(), BEFORE.name(), value);
      assertThrows(CriteriaException.class, () -> validator.validate(f));
    }
  }

  @Nested
  class ValidateCriteriaValues {

    @ParameterizedTest
    @ValueSource(strings = {"greater_than", "less_than", "equal_to"})
    void shouldSuccessfullyValidateAmountOperator(String operator) {
      Filter f = new Filter(AMOUNT.name(), operator, AMOUNT_VALUE);
      assertDoesNotThrow(() -> validator.validate(f));
    }

    @ParameterizedTest
    @ValueSource(strings = {"equals", "contains", "starts_with", "ends_with"})
    void shouldSuccessfullyValidateTitleOperator(String operator) {
      Filter f = new Filter(TITLE.name(), operator, TITLE_VALUE);
      assertDoesNotThrow(() -> validator.validate(f));
    }

    @ParameterizedTest
    @ValueSource(strings = {"before", "after", "equals"})
    void shouldSuccessfullyValidateDateOperator(String operator) {
      Filter f = new Filter(DATE.name(), operator, DATE_VALUE);
      assertDoesNotThrow(() -> validator.validate(f));
    }
  }
}
