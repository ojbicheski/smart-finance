package com.smartfinance.product.controller.v1.impl;

import com.smartfinance.product.controller.v1.ProductDetailController;
import com.smartfinance.product.dto.ProductDetailDTO;
import com.smartfinance.product.service.ProductDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product/details")
@AllArgsConstructor
public class ProductDetailControllerImpl implements ProductDetailController {
  private final ProductDetailService service;

  @PostMapping
  @Override
  public ResponseEntity<ProductDetailDTO> save(ProductDetailDTO dto) {
    return ResponseEntity.ok(service.save(dto));
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<ProductDetailDTO> find(UUID reference) {
    return ResponseEntity.ok(service.find(reference));
  }

  @GetMapping
  @Override
  public List<ProductDetailDTO> list(UUID product, boolean active) {
    return service.list(product, active);
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

  @Override
  public ResponseEntity<Void> delete(UUID reference) {
    service.delete(reference);
    return ResponseEntity.noContent().build();
  }
}
