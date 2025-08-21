package com.smartfinance.client.template.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Template {
  private UUID reference;
  private String name;
  private String description;
  private String templateVersion;
  private Format format;
  private Layout layout;

  @Setter
  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Layout {
    private List<IgnoreLine> ignoreLines;
    private List<Position> positions;
  }

  @Setter
  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class IgnoreLine {
    private String starts; // Top-Down; Bottom-Up
    private int line;
  }

  @Setter
  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Position {
    private int sequence;
    private String type;
    private String format;
    private String mapTo;
    private String function;
    private String set;
    private String[] removeCharacters;
  }
}
