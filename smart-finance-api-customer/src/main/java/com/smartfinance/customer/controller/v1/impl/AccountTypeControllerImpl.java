package com.smartfinance.customer.controller.v1.impl;

import com.smartfinance.customer.controller.v1.AccountTypeController;
import com.smartfinance.customer.dto.AccountTypeDTO;
import com.smartfinance.customer.service.AccountTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account-types")
@AllArgsConstructor
public class AccountTypeControllerImpl implements AccountTypeController {
  private final AccountTypeService service;

  @PostMapping
  @Override
  public ResponseEntity<AccountTypeDTO> save(AccountTypeDTO dto) {
    return ResponseEntity.ok(service.save(dto));
  }

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> del(UUID reference) {
    service.delete(reference);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<AccountTypeDTO> get(UUID reference) {
    return ResponseEntity.ok(service.get(reference));
  }

  @GetMapping
  @Override
  public List<AccountTypeDTO> list() {
    return service.list();
  }
}
