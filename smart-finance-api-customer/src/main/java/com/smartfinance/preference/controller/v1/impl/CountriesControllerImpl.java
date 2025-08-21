package com.smartfinance.preference.controller.v1.impl;

import com.smartfinance.preference.controller.v1.CountriesController;
import com.smartfinance.preference.service.CountriesService;
import com.smartfinance.shared.dto.CountryDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers/{customer}/preferences/countries")
public class CountriesControllerImpl implements CountriesController {
  private final CountriesService service;

  @GetMapping
  @Override
  public List<CountryDTO> countries(UUID customer) {
    return service.getCountries(customer);
  }

  @PostMapping
  @Override
  public ResponseEntity<Void> save(UUID customer, List<CountryDTO> countries) {
    return ResponseEntity.ok().build();
  }
}
