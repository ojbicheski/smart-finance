package com.smartfinance.loader.mq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartfinance.loader.entity.Line;
import com.smartfinance.loader.exception.InternalServerErrorException;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transact {
  private FileTransact file;
  private LineTransact line;
  private Event event;
  @Setter
  private ResponseTransact response;

  @Builder
  public Transact(TriggerTransact trigger, Line line, Event event) {
    this.file = FileTransact.builder()
        .account(trigger.account())
        .reference(trigger.uuid())
        .fileName(trigger.getFileName())
        .build();
    this.line = LineTransact.builder()
        .id(line.getId())
        .number(line.getLineNumber())
        .build();
    this.event = event;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Event {
    private LocalDate date;
    private String name;
    private String description;
    private String type;
    private float amount;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class FileTransact {
    private UUID account;
    private UUID reference;
    private String fileName;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LineTransact {
    private long id;
    private int number;
  }

  @Getter
  @Setter
  @ToString
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ResponseTransact {
    private String status;
    private String message;
  }

  public byte[] bytes() {
    try {
      ObjectMapper om = new ObjectMapper();
      om.registerModule(new JavaTimeModule());
      return om.writeValueAsString(this).getBytes();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException("Failed build Message.", e);
    }
  }
}
