package com.smartfinance.customer.dto;

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
public class AccountTypeDTO {
  private UUID reference;
  @NotNull(message = "{messages.validation.accountType.description.NotNull}")
  @Size(min = 5, max = 15, message = "{messages.validation.accountType.description.Size}")
  private String description;
  private boolean active;
}
