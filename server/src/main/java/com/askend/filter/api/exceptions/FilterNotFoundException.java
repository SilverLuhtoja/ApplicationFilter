package com.askend.filter.api.exceptions;

public class FilterNotFoundException extends RuntimeException {

  public FilterNotFoundException() {
    super("Filter Not Found");
  }
}
