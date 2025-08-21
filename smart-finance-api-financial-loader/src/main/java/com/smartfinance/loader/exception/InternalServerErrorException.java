package com.smartfinance.loader.exception;

import com.smartfinance.exception.APIException;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends APIException {
  public InternalServerErrorException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public InternalServerErrorException(String message, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, message, cause);
  }
}
