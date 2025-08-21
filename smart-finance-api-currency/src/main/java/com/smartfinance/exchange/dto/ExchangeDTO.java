package com.smartfinance.exchange.dto;

import com.smartfinance.currency.dto.CurrencyDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeDTO {
  private UUID reference;
  private LocalDate exchanged;
  private double value;

  private CurrencyDTO from;
  private CurrencyDTO to;
}
