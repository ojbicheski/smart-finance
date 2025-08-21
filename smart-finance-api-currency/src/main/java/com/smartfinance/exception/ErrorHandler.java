package com.smartfinance.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(APIException.class)
  public ResponseEntity<Object> handleAPIException(APIException exception, WebRequest request) {
    String user = user();
    log.error("\nUser: [{}]\nAPI call: [{}] ", user, exception.getMessage(), exception);

    return handleExceptionInternal(
        exception,
        exception.getError(),
        new HttpHeaders(),
        exception.getStatus(),
        request
    );
  }

  private String user() {
    StringBuilder buffer = new StringBuilder();
//    SecurityUtils.getCurrentUser().ifPresent(auth ->
//        buffer.append(auth.getName()).append("::").append(auth.getOperatorId()));

    return buffer.length() > 0 ? buffer.toString() : "Not informed";
  }
}
