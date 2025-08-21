package com.smartfinance.finance.dto.expense;

import com.smartfinance.customer.dto.CustomerDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GroupDTO {
  private UUID reference;
  @NotNull(message = "{messages.validation.expense.group.name.NotNull}")
  @Size(min = 2, max = 100, message = "{messages.validation.expense.group.name.Size}")
  private String name;
  @NotNull(message = "{messages.validation.expense.group.description.NotNull}")
  @Size(min = 8, max = 255, message = "{messages.validation.expense.group.description.Size}")
  private String description;
  private boolean active;
  private CustomerDTO customer;
}
