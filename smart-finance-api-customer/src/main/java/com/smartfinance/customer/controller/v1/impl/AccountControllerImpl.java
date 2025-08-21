package com.smartfinance.customer.controller.v1.impl;

import com.smartfinance.customer.controller.v1.AccountController;
import com.smartfinance.customer.dto.AccountDTO;
import com.smartfinance.customer.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/accounts")
@AllArgsConstructor
public class AccountControllerImpl implements AccountController {
  private final AccountService service;

  @PostMapping
  @Override
  public ResponseEntity<AccountDTO> save(UUID customer, AccountDTO dto) {
    return ResponseEntity.ok(service.save(customer, dto));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<AccountDTO> get(UUID reference) {
    return ResponseEntity.ok(service.get(reference));
  }

  @PutMapping("/{reference}/activate")
  @Override
  public ResponseEntity<Void> activate(UUID reference) {
    service.activate(reference);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{reference}/deactivate")
  @Override
  public ResponseEntity<Void> deactivate(UUID reference) {
    service.deactivate(reference);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> del(UUID reference) {
    service.delete(reference);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Override
  public List<AccountDTO> list(UUID customer, boolean active) {
    return service.list(customer, active);
  }
}
