package com.smartfinance.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerDTO {
  private UUID reference;
  private String name;
}
