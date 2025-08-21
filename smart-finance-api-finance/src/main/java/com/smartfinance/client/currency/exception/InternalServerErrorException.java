package com.smartfinance.client.currency.exception;

import com.smartfinance.exception.APIException;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends APIException {
  public InternalServerErrorException(String message, Object data) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, data);
  }
}
