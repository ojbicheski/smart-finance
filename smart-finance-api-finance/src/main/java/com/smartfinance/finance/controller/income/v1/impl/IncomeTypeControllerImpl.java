package com.smartfinance.finance.controller.income.v1.impl;

import com.smartfinance.finance.controller.expense.v1.GroupController;
import com.smartfinance.finance.dto.expense.GroupDTO;
import com.smartfinance.finance.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
@AllArgsConstructor
public class IncomeTypeControllerImpl implements GroupController {
  private final GroupService service;

  @PostMapping
  @Override
  public ResponseEntity<GroupDTO> save(UUID customer, GroupDTO dto) {
    return ResponseEntity.ok(service.save(customer, dto));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<GroupDTO> find(UUID reference) {
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
  public ResponseEntity<GroupDTO> activate(UUID reference) {
    return ResponseEntity.ok(service.activate(reference));
  }

  @PutMapping("/{reference}/deactivate")
  @Override
  public ResponseEntity<GroupDTO> deactivate(UUID reference) {
    return ResponseEntity.ok(service.deactivate(reference));
  }

  @GetMapping
  @Override
  public List<GroupDTO> list(UUID customer, boolean active) {
    return service.list(customer, active);
  }

  @PostMapping("/search")
  @Override
  public List<GroupDTO> search(UUID customer, GroupDTO dto) {
    return List.of();
  }
}
