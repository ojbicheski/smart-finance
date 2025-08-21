package com.smartfinance.template.controller.v1.impl;

import com.smartfinance.template.controller.v1.TemplateController;
import com.smartfinance.template.dto.TemplateDTO;
import com.smartfinance.template.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/templates")
@AllArgsConstructor
public class TemplateControllerImpl implements TemplateController {
  private final TemplateService service;

  @PostMapping
  @Override
  public ResponseEntity<TemplateDTO> save(UUID operator, TemplateDTO template) {
    return ResponseEntity.ok(service.save(operator, template));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<TemplateDTO> get(UUID reference) {
    return ResponseEntity.ok(service.find(reference));
  }

  @GetMapping
  @Override
  public List<TemplateDTO> list(UUID operator, boolean active) {
    return service.list(operator, active);
  }

  @GetMapping("/{reference}/activate")
  @Override
  public ResponseEntity<Void> activate(UUID reference) {
    return null;
  }

  @GetMapping("/{reference}/deactivate")
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
