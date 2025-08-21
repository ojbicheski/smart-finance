package com.smartfinance.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartfinance.currency.dto.CurrencyDTO;
import com.smartfinance.operator.dto.OperatorDTO;
import com.smartfinance.operator.dto.ProductDetailDTO;
import com.smartfinance.operator.dto.TemplateDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
  private String reference;
  private String number;
  private String description;

  private CustomerDTO customer;
  private AccountTypeDTO type;
  private OperatorDTO operator;
  private ProductDetailDTO detail;
  private CurrencyDTO currency;

  private TemplateDTO template;
}
