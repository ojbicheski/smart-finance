package com.smartfinance.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateFromEpochDeserializer extends StdDeserializer<LocalDate> {

  protected LocalDateFromEpochDeserializer() {
    super(LocalDate.class);
  }

  @Override
  public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
    return Instant
        .ofEpochMilli(jsonParser.readValueAs(Long.class))
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
  }
}
