package com.smartfinance.loader.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LineDTO {
  private UUID reference;
  private int lineNumber;
  private String line;
  private String status;
}
