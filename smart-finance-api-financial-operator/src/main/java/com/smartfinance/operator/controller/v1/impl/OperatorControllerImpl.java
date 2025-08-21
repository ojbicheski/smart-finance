package com.smartfinance.operator.controller.v1.impl;

import com.smartfinance.operator.controller.v1.OperatorController;
import com.smartfinance.operator.dto.OperatorDTO;
import com.smartfinance.operator.service.OperatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/operators")
@AllArgsConstructor
public class OperatorControllerImpl implements OperatorController {
  private final OperatorService service;

  @PostMapping
  @Override
  public ResponseEntity<OperatorDTO> save(UUID country, OperatorDTO operatorDTO) {
    return ResponseEntity.ok(service.save(country, operatorDTO));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<OperatorDTO> get(UUID reference) {
    return ResponseEntity.ok(service.find(reference));
  }

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> delete(UUID reference) {
    service.delete(reference);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  @Override
  public List<OperatorDTO> list(UUID country) {
    return service.list(country);
  }
}
