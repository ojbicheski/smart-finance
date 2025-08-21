package com.smartfinance.client.template.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Format {
  private UUID reference;
  @NotNull(message = "{messages.validation.format.extension.NotNull}")
  @Size(min = 3, max = 5, message = "{messages.validation.format.extension.Size}")
  private String extension;
  private boolean active;
}
