package com.smartfinance.loader.exception;

import com.smartfinance.exception.APIException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends APIException {
  public BadRequestException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
