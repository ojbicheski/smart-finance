package com.smartfinance.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
  @Builder.Default
  private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("UTC"));
  @Builder.Default
  private final ZoneId timezone = ZoneId.of("UTC");
  private final HttpStatus status;
  private final String message;
  private final Object data;
}
