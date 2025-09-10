package com.askend.filter.api.models.enums;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum FilterCriteria {
  AMOUNT(CriteriaAmountOperators.class),
  TITLE(CriteriaTitleOperators.class),
  DATE(CriteriaDateOperators.class);

  @Getter
  private final List<String> operators;

  <E extends Enum<E> & CriteriaOperator> FilterCriteria(Class<E> operatorEnum) {
    this.operators = Arrays.stream(operatorEnum.getEnumConstants())
        .map(Enum::name)
        .toList();
  }

  public enum CriteriaAmountOperators implements CriteriaOperator {
    GREATER_THAN,
    LESS_THAN,
    EQUAL_TO
  }


  public enum CriteriaTitleOperators implements CriteriaOperator {
    EQUALS,
    CONTAINS,
    STARTS_WITH,
    ENDS_WITH
  }

  public enum CriteriaDateOperators implements CriteriaOperator {
    BEFORE,
    AFTER,
    EQUALS
  }
}

