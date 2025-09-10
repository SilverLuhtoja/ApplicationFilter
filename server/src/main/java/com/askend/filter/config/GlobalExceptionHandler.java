package com.askend.filter.config;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.askend.filter.api.exceptions.CriteriaException;
import com.askend.filter.api.exceptions.FilterNotFoundException;
import com.askend.filter.api.exceptions.InvalidValueException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidValueException.class)
  public ResponseEntity<Object> handleInvalidValueException(InvalidValueException ex) {
    return buildResponse(ex, BAD_REQUEST);
  }

  @ExceptionHandler(CriteriaException.class)
  public ResponseEntity<Object> handleCriteriaException(CriteriaException ex) {
    return buildResponse(ex, BAD_REQUEST);
  }

  @ExceptionHandler(FilterNotFoundException.class)
  public ResponseEntity<Object> handleFilterNotFoundException(FilterNotFoundException ex) {
    return buildResponse(ex, NOT_FOUND);
  }

  private ResponseEntity<Object> buildResponse(Exception ex, HttpStatus status) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, status);
  }
}
