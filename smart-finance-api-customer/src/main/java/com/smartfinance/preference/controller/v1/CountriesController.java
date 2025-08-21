package com.smartfinance.preference.controller.v1;

import com.smartfinance.customer.controller.CommonController;
import com.smartfinance.shared.dto.CountryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

public interface CountriesController extends CommonController {
  List<CountryDTO> countries(
      @PathVariable UUID customer);
  ResponseEntity<Void> save(
      @PathVariable("x-sf-customer-ref") UUID customer,
      @RequestBody List<CountryDTO> countries);
}
