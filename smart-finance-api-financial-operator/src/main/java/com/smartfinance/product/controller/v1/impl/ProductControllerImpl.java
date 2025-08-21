package com.smartfinance.product.controller.v1.impl;

import com.smartfinance.product.controller.v1.ProductController;
import com.smartfinance.product.dto.ProductDTO;
import com.smartfinance.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {
  private final ProductService service;

  @PostMapping
  @Override
  public ResponseEntity<ProductDTO> save(ProductDTO dto) {
    return ResponseEntity.ok(service.save(dto));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<ProductDTO> find(UUID reference) {
    return ResponseEntity.ok(service.find(reference));
  }

  @GetMapping
  @Override
  public List<ProductDTO> list(UUID operator, boolean active) {
    return service.list(operator, active);
  }

  @PutMapping("/{reference}/activate")
  @Override
  public ResponseEntity<Void> activate(UUID reference) {
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{reference}/deactivate")
  @Override
  public ResponseEntity<Void> deactivate(UUID reference) {
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> delete(UUID reference) {
    service.delete(reference);
    return ResponseEntity.noContent().build();
  }
}
