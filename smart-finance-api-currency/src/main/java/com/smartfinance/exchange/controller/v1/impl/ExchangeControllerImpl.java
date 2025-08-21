package com.smartfinance.exchange.controller.v1.impl;

import com.smartfinance.currency.controller.v1.CurrencyController;
import com.smartfinance.currency.dto.CurrencyDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/currencies")
public class ExchangeControllerImpl implements CurrencyController {
  private final com.smartfinance.currency.service.CurrencyService service;

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> del(UUID reference) {
    service.delete(reference);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<CurrencyDTO> get(UUID reference) {
    return ResponseEntity.ok(service.find(reference));
  }

  @PostMapping
  @Override
  public ResponseEntity<CurrencyDTO> save(CurrencyDTO currencyDTO) {
    return ResponseEntity.ok(service.save(currencyDTO));
  }

  @GetMapping
  @Override
  public Page<CurrencyDTO> list(String name, int page, int size, String orderBy, String direction) {
    return service.list(name, page(page, size, orderBy, direction));
  }
}
