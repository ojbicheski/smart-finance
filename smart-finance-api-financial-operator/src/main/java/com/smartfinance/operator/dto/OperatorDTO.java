package com.smartfinance.operator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class OperatorDTO {
  private UUID reference;
  @NotNull(message = "{messages.validation.operator.swiftCode.NotNull}")
  @Size(min = 8, max = 15, message = "{messages.validation.operator.swiftCode.Size}")
  private String swiftCode;
  @NotNull(message = "{messages.validation.operator.name.NotNull}")
  @Size(min = 4, max = 255, message = "{messages.validation.operator.name.Size}")
  private String name;
  @NotNull(message = "{messages.validation.operator.name.NotNull}")
  @Size(min = 2, max = 100, message = "{messages.validation.operator.name.Size}")
  private String display;
  private boolean active;
  private CountryDTO country;
}
