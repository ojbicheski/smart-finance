package com.smartfinance.template.controller.v1.impl;

import com.smartfinance.template.controller.v1.FormatController;
import com.smartfinance.template.dto.FormatDTO;
import com.smartfinance.template.service.FormatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/formats")
@AllArgsConstructor
public class FormatControllerImpl implements FormatController {
  private final FormatService service;

  @PostMapping
  @Override
  public ResponseEntity<FormatDTO> save(UUID operator, FormatDTO format) {
    return ResponseEntity.ok(service.save(operator, format));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<FormatDTO> get(UUID reference) {
    return ResponseEntity.ok(service.find(reference));
  }

  @GetMapping
  @Override
  public List<FormatDTO> list(UUID operator, boolean active) {
    return service.list(operator, active);
  }

  @Override
  public ResponseEntity<Void> activate(UUID reference) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deactivate(UUID reference) {
    return null;
  }

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> delete(UUID reference) {
    service.delete(reference);
    return ResponseEntity.noContent().build();
  }
}
