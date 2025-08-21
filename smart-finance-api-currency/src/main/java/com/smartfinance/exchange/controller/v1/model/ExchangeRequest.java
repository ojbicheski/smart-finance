package com.smartfinance.exchange.controller.v1.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExchangeRequest {
  @NotNull(message = "{messages.validation.exchange.request.countries-date.from.NotNull}")
  UUID from;
  @NotNull(message = "{messages.validation.exchange.request.countries-date.countries.NotNull}")
  List<UUID> countries;
  @NotNull(message = "{messages.validation.exchange.request.countries-date.target.NotNull}")
  LocalDate target;
}
