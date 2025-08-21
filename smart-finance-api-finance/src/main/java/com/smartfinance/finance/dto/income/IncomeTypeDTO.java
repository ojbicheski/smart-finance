package com.smartfinance.finance.dto.income;

import com.smartfinance.customer.dto.CustomerDTO;
import com.smartfinance.finance.dto.expense.GroupDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class IncomeTypeDTO {
  private UUID reference;
  @NotNull(message = "{messages.validation.income.type.name.NotNull}")
  @Size(min = 2, max = 100, message = "{messages.validation.income.type.name.Size}")
  private String name;
  @NotNull(message = "{messages.validation.income.type.description.NotNull}")
  @Size(min = 8, max = 255, message = "{messages.validation.income.type.description.Size}")
  private String description;
  private boolean active;
  private CustomerDTO customer;
}
