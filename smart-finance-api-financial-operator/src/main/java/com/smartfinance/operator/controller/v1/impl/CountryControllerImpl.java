package com.smartfinance.operator.controller.v1.impl;

import com.smartfinance.operator.controller.v1.CountryController;
import com.smartfinance.operator.dto.CountryDTO;
import com.smartfinance.operator.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
@AllArgsConstructor
public class CountryControllerImpl implements CountryController {
  private final CountryService service;

  @GetMapping
  @Override
  public List<CountryDTO> list() {
    return service.list();
  }
}
