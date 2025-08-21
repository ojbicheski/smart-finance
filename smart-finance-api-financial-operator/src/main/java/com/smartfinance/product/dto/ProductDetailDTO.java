package com.smartfinance.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartfinance.template.dto.TemplateDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailDTO {
  private UUID reference;
  private String code;
  private String name;
  private boolean active;

  private TypeDTO type;
  private TemplateDTO template;
}
