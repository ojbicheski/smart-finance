package com.smartfinance.currency.controller.v1;

import com.smartfinance.currency.controller.CommonController;
import com.smartfinance.currency.dto.CurrencyDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface CurrencyController extends CommonController {
  ResponseEntity<Void> del(@PathVariable UUID reference);
  ResponseEntity<CurrencyDTO> get(@PathVariable UUID reference);
  ResponseEntity<CurrencyDTO> save(@RequestBody CurrencyDTO currencyDTO);
  Page<CurrencyDTO> list(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "3") int size,
      @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction);
}
