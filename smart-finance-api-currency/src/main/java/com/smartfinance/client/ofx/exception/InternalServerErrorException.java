package com.smartfinance.client.ofx.exception;

import com.smartfinance.exception.APIException;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends APIException {
  public InternalServerErrorException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }
  public InternalServerErrorException(String message, Throwable exception) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, exception);
  }
  public InternalServerErrorException(String message, Object data) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, data);
  }
}
