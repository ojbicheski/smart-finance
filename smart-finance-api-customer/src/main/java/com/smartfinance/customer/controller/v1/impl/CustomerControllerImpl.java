package com.smartfinance.customer.controller.v1.impl;

import com.smartfinance.customer.controller.v1.CustomerController;
import com.smartfinance.customer.dto.CustomerDTO;
import com.smartfinance.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllerImpl implements CustomerController {
  private final CustomerService service;

  @DeleteMapping("/{reference}")
  @Override
  public ResponseEntity<Void> del(UUID reference) {
    service.delete(reference);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{reference}")
  @Override
  public ResponseEntity<CustomerDTO> get(UUID reference) {
    return ResponseEntity.ok(service.get(reference));
  }

  @PostMapping
  @Override
  public ResponseEntity<CustomerDTO> save(CustomerDTO customerDTO) {
    return ResponseEntity.ok(service.save(customerDTO));
  }

  @GetMapping
  @Override
  public Page<CustomerDTO> list(String name, int page, int size, String orderBy, String direction) {
    return service.list(name, page(page, size, orderBy, direction));
  }
}
