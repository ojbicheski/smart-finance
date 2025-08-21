package com.smartfinance.loader.mq.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfinance.loader.exception.InternalServerErrorException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Transactions {
  private final FileTransact file;
  private final List<Transact> events;

  @Getter
  @Builder
  public static class FileTransact {
    private final UUID reference;
    private final String fileName;
  }

  public byte[] bytes() {
    try {
      return new ObjectMapper().writeValueAsString(this).getBytes();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException("Failed build Message.", e);
    }
  }
}
