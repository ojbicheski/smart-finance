package com.smartfinance.loader.exception;

import com.smartfinance.exception.APIException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends APIException {
  public NotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
