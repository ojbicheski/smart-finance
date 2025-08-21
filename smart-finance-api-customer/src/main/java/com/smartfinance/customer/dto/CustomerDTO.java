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
public class CustomerDTO {
  private UUID reference;
  @NotNull(message = "{messages.validation.accountType.description.NotNull}")
  @Size(min = 8, max = 255, message = "{messages.validation.customer.name.Size}")
  private String name;
}
