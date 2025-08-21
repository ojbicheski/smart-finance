package com.smartfinance.transaction.mq.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smartfinance.config.JacksonConfig;
import com.smartfinance.transaction.exception.InternalServerErrorException;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FinanceEvent {
  private UUID customer;
  private UUID account;
  private long transaction;

  public byte[] bytes() {
    try {
      return new JacksonConfig().objectMapper()
          .writeValueAsString(this)
          .getBytes();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException("Failed build Message.", e);
    }
  }
}
