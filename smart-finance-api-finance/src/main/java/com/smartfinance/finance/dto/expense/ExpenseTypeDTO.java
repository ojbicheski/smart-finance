package com.smartfinance.finance.dto.expense;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ExpenseTypeDTO {
  private UUID reference;
  @NotNull(message = "{messages.validation.expense.group.type.name.NotNull}")
  @Size(min = 2, max = 100, message = "{messages.validation.expense.group.type.name.Size}")
  private String name;
  @NotNull(message = "{messages.validation.expense.group.type.description.NotNull}")
  @Size(min = 8, max = 255, message = "{messages.validation.expense.group.type.description.Size}")
  private String description;
  private boolean active;
  private GroupDTO group;
}
