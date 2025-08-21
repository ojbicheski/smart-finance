package com.smartfinance.finance.controller.income.v1.impl;

import com.smartfinance.finance.controller.income.v1.IncomeTypeController;
import com.smartfinance.finance.dto.income.IncomeTypeDTO;
import com.smartfinance.finance.service.IncomeTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/income/types")
@AllArgsConstructor
public class IncomeTypeControllerImpl implements IncomeTypeController {
  private final IncomeTypeService service;

  @PostMapping
  @Override
  public ResponseEntity<IncomeTypeDTO> save(UUID customer, IncomeTypeDTO dto) {
    return ResponseEntity.ok(service.save(customer, dto));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<IncomeTypeDTO> find(UUID reference) {
    return ResponseEntity.ok(service.find(reference));
  }

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> delete(UUID reference) {
    service.delete(reference);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{reference}/activate")
  @Override
  public ResponseEntity<IncomeTypeDTO> activate(UUID reference) {
    return ResponseEntity.ok(service.activate(reference));
  }

  @PutMapping("/{reference}/deactivate")
  @Override
  public ResponseEntity<IncomeTypeDTO> deactivate(UUID reference) {
    return ResponseEntity.ok(service.deactivate(reference));
  }

  @GetMapping
  @Override
  public List<IncomeTypeDTO> list(UUID customer, boolean active) {
    return service.list(customer, active);
  }

  @PostMapping("/search")
  @Override
  public List<IncomeTypeDTO> search(UUID customer, IncomeTypeDTO dto) {
    return List.of();
  }
}
