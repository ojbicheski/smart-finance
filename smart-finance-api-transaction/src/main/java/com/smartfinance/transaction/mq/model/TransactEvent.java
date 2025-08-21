package com.smartfinance.transaction.mq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.smartfinance.config.JacksonConfig;
import com.smartfinance.transaction.exception.InternalServerErrorException;
import lombok.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactEvent {
  private FileTransact file;
  private LineTransact line;
  private Event event;
  private ResponseTransact response;

  @Getter
  @Setter
  @ToString
  public static class Event {
    private LocalDate date;
    private String name;
    private String description;
    private String type;
    @JsonDeserialize(using = FloatTwoDecimalPlacesDeserializer.class)
    private float amount;
  }

  @Getter
  @Setter
  @ToString
  public static class FileTransact {
    private UUID account;
    private UUID customer;
    private UUID reference;
    private String fileName;
  }

  @Getter
  @Setter
  @ToString
  public static class LineTransact {
    private long id;
    private int number;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class ResponseTransact {
    private String status;
    private String message;
  }

  public byte[] bytes() {
    try {
      return new JacksonConfig().objectMapper()
          .writeValueAsString(this)
          .getBytes();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException("Failed build Message.", e);
    }
  }

  public static class FloatTwoDecimalPlacesDeserializer extends StdDeserializer<Float> {
    public FloatTwoDecimalPlacesDeserializer() {
      super(Float.class);
    }

    @Override
    public Float deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException {
      return BigDecimal.valueOf(jp.getFloatValue())
          .setScale(2, RoundingMode.HALF_UP)
          .floatValue();
    }
  }
}
