package com.smartfinance.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartfinance.operator.dto.OperatorDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeDTO {
  private UUID reference;
  private String code;
  private String name;
  private boolean active;

  private OperatorDTO operator;
}
