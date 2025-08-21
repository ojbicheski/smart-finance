package com.smartfinance.currency.dto;

import com.smartfinance.shared.dto.CountryDTO;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyDTO {
  private UUID reference;
  private String name;
  private String code;
  private String symbol;
  private boolean active;
  private CountryDTO country;
}
