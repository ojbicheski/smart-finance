package com.smartfinance.client.currency.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CountriesDateRequest {
  UUID from;
  List<UUID> countries;
  LocalDate target;
}
