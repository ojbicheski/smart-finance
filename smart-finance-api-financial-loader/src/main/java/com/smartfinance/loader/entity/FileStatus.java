package com.smartfinance.loader.entity;

import com.smartfinance.loader.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum FileStatus {
  UPLOADED("File imported", Stream.of("LOADED", "FAIL_TO_LOAD")),
  LOADED("File loaded", Stream.of()),
  FAIL_TO_LOAD("Fail to load same lines", Stream.of("LOADED")),
  WARNING("File warned", Stream.of("LOADED", "FAIL_TO_LOAD"));

  private final String description;
  private final Stream<String> next;

  public static FileStatus of(String status) {
    return Arrays.stream(values())
        .filter(t -> t.name().equalsIgnoreCase(status))
        .findFirst()
        .orElseThrow(() -> new BadRequestException("File Status %s is invalid.".formatted(status)));
  }
}