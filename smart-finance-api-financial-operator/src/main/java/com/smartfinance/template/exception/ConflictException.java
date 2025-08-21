package com.smartfinance.template.exception;

import com.smartfinance.exception.APIException;
import org.springframework.http.HttpStatus;

public class ConflictException extends APIException {
  public ConflictException(String message, Throwable cause) {
    super(HttpStatus.CONFLICT, message, cause);
  }
}
