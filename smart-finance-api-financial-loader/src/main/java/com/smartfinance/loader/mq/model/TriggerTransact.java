package com.smartfinance.loader.mq.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfinance.loader.exception.InternalServerErrorException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TriggerTransact {
  private final String fileName;
  private final String reference;

  public byte[] bytes() {
    try {
      return new ObjectMapper().writeValueAsString(this).getBytes();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException("Failed build Message.", e);
    }
  }
}
