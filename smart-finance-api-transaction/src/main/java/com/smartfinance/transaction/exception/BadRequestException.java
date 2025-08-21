package com.smartfinance.transaction.exception;

import com.smartfinance.exception.APIException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends APIException {
  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, message, cause);
  }
}
