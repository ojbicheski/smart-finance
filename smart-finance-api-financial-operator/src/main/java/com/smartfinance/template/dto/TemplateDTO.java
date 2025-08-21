package com.smartfinance.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartfinance.template.entity.Template;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Setter
@Getter
@JsonInclude(NON_NULL)
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
    private List<IgnoreLine> ignoreLines;
    private List<Position> positions;
  }

  @Setter
  @Getter
  public static class IgnoreLine {
    private String starts; // Top-Down; Bottom-Up
    private int line;
  }

  @Setter
  @Getter
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
