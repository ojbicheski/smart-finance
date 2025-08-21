package com.smartfinance.template.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class TemplateDTO {
  private UUID reference;
  private String name;
  private String description;
  private String templateVersion;
  private boolean active;
  private FormatDTO format;
  private Layout layout;

  @Setter
  @Getter
  public static class Layout {
    private int[] ignoreLines;
    private List<Position> positions;
  }

  @Setter
  @Getter
  public static class Position {
    private int sequence;
    private String type;
    private String mapTo;
    private String[] removeCharacters;
  }
}
