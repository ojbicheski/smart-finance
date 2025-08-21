package com.smartfinance.shared.dto;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryDTO {
  private UUID reference;
  private String name;
  private String code;
  private String display;
}
