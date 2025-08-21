package com.smartfinance.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIException extends RuntimeException {
  private final HttpStatus status;
  private final Object data;

  public APIException(HttpStatus status, String message) {
    super(message);
    this.status = status;
    this.data = null;
  }

  public APIException(HttpStatus status, String message, Throwable cause) {
    super(message, cause);
    this.status = status;
    this.data = null;
  }

  public APIException(HttpStatus status, String message, Object data) {
    super(message);
    this.status = status;
    this.data = data;
  }

  public APIException(HttpStatus status, String message, Throwable cause, Object data) {
    super(message, cause);
    this.status = status;
    this.data = data;
  }

  public Error getError() {
    return Error.builder()
        .message(getMessage())
        .status(getStatus())
        .data(getData())
        .build();
  }
}
