package com.smartfinance.client.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exchange {
  private UUID reference;
  private LocalDate exchanged;
  private float value;

  private Currency from;
  private Currency to;

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Currency {
    private UUID reference;
    private String code;
    private Country country;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Country {
    private UUID reference;
    private String code;
  }
}
