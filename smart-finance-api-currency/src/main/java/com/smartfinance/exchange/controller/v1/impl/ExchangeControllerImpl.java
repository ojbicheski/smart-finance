package com.smartfinance.exchange.controller.v1.impl;

import com.smartfinance.exchange.controller.v1.ExchangeController;
import com.smartfinance.exchange.controller.v1.model.ExchangeRequest;
import com.smartfinance.exchange.dto.ExchangeDTO;
import com.smartfinance.exchange.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/exchanges")
public class ExchangeControllerImpl implements ExchangeController {
  private final ExchangeService service;

  @PostMapping("/countries-date")
  @Override
  public List<ExchangeDTO> exchanges(ExchangeRequest request) {
    return service.exchanges(
        request.getFrom(), request.getCountries(), request.getTarget());
  }
}