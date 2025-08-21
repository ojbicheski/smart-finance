package com.smartfinance.product.controller.v1.impl;

import com.smartfinance.operator.dto.CountryDTO;
import com.smartfinance.operator.service.CountryService;
import com.smartfinance.product.controller.v1.ProductController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {
  private final CountryService service;

  @GetMapping
  @Override
  public List<CountryDTO> list() {
    return service.list();
  }
}
