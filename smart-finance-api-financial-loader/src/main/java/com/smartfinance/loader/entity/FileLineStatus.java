package com.smartfinance.loader.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum FileLineStatus {
  IMPORTED("Line imported", Stream.of("LOADED", "FAIL_TO_LOAD")),
  LOADED("Line loaded", Stream.of()),
  FAIL_TO_LOAD("Fail to load line", Stream.of("LOADED"));

  private final String description;
  private final Stream<String> next;
}
