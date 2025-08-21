package com.smartfinance.loader.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum FileLineStatus {
  IMPORTED("Line imported", Stream.of("LOADED", "FAIL_TO_LOAD", "DUPLICATED")),
  LOADED("Line loaded", Stream.of()),
  FAIL_TO_LOAD("Fail to load line", Stream.of("LOADED")),
  DUPLICATED("Line duplicated", Stream.of("LOADED", "FAIL_TO_LOAD"));

  private final String description;
  private final Stream<String> next;
}
