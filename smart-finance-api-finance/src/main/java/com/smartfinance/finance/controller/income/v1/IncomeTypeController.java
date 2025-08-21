package com.smartfinance.finance.controller.income.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.finance.dto.income.IncomeTypeDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface IncomeTypeController extends CommonController {
  ResponseEntity<IncomeTypeDTO> save(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @Valid @RequestBody IncomeTypeDTO dto);
  ResponseEntity<IncomeTypeDTO> find(
      @PathVariable("reference") UUID reference);
  ResponseEntity<Void> delete(
      @PathVariable("reference") UUID reference);
  ResponseEntity<IncomeTypeDTO> activate(
      @PathVariable("reference") UUID reference);
  ResponseEntity<IncomeTypeDTO> deactivate(
      @PathVariable("reference") UUID reference);
  List<IncomeTypeDTO> list(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @RequestParam(defaultValue = "true") boolean active);
  List<IncomeTypeDTO> search(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @RequestBody IncomeTypeDTO dto);
}
