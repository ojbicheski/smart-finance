package com.smartfinance.loader.mq.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfinance.loader.exception.InternalServerErrorException;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TriggerTransact {
  private String account;
  private String file;
  private String fileName;
  private String template;
  private int totalLines;

  public byte[] bytes() {
    try {
      return new ObjectMapper().writeValueAsString(this).getBytes();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException("Failed build Message.", e);
    }
  }

  public UUID uuid() {
    return UUID.fromString(file);
  }
  public UUID account() {
    return UUID.fromString(account);
  }
  public UUID template() {
    return UUID.fromString(template);
  }
}
