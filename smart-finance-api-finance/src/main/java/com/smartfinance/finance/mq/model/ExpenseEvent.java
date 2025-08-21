package com.smartfinance.finance.mq.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smartfinance.config.JacksonConfig;
import com.smartfinance.finance.exception.InternalServerErrorException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ExpenseEvent {
  private UUID customer;
  private UUID transaction;
  private LocalDate date;

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
