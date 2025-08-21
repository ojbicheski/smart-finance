package com.smartfinance.product.controller.v1.impl;

import com.smartfinance.product.controller.v1.TypeController;
import com.smartfinance.product.dto.TypeDTO;
import com.smartfinance.product.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product/types")
@AllArgsConstructor
public class TypeControllerImpl implements TypeController {
  private final TypeService service;

  @PostMapping
  @Override
  public ResponseEntity<TypeDTO> save(UUID operator, TypeDTO dto) {
    return ResponseEntity.ok(service.save(operator, dto));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<TypeDTO> find(UUID reference) {
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
  public List<TypeDTO> list(UUID operator, boolean active) {
    return service.list(operator, active);
  }
}
