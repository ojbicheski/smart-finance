package com.smartfinance.finance.controller.expense.v1.impl;

import com.smartfinance.finance.controller.expense.v1.ExpenseTypeController;
import com.smartfinance.finance.dto.expense.ExpenseTypeDTO;
import com.smartfinance.finance.service.ExpenseTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expense/group/types")
@AllArgsConstructor
public class ExpenseTypeControllerImpl implements ExpenseTypeController {
  private final ExpenseTypeService service;

  @PostMapping
  @Override
  public ResponseEntity<ExpenseTypeDTO> save(UUID group, ExpenseTypeDTO dto) {
    return ResponseEntity.ok(service.save(group, dto));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<ExpenseTypeDTO> find(UUID reference) {
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
  public ResponseEntity<ExpenseTypeDTO> activate(UUID reference) {
    return ResponseEntity.ok(service.activate(reference));
  }

  @PutMapping("/{reference}/deactivate")
  @Override
  public ResponseEntity<ExpenseTypeDTO> deactivate(UUID reference) {
    return ResponseEntity.ok(service.deactivate(reference));
  }

  @GetMapping
  @Override
  public List<ExpenseTypeDTO> list(UUID group, boolean active) {
    return service.list(group, active);
  }

  @PostMapping("/search")
  @Override
  public List<ExpenseTypeDTO> search(UUID group, ExpenseTypeDTO dto) {
    return List.of();
  }
}
