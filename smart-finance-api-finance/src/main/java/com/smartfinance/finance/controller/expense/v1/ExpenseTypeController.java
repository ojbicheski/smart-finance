package com.smartfinance.finance.controller.expense.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.finance.dto.expense.ExpenseTypeDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface ExpenseTypeController extends CommonController {
  ResponseEntity<ExpenseTypeDTO> save(
      @RequestHeader("x-sf-group-ref") UUID group,
      @Valid @RequestBody ExpenseTypeDTO dto);
  ResponseEntity<ExpenseTypeDTO> find(
      @PathVariable("reference") UUID reference);
  ResponseEntity<Void> delete(
      @PathVariable("reference") UUID reference);
  ResponseEntity<ExpenseTypeDTO> activate(
      @PathVariable("reference") UUID reference);
  ResponseEntity<ExpenseTypeDTO> deactivate(
      @PathVariable("reference") UUID reference);
  List<ExpenseTypeDTO> list(
      @RequestHeader("x-sf-group-ref") UUID group,
      @RequestParam(defaultValue = "true") boolean active);
  List<ExpenseTypeDTO> search(
      @RequestHeader("x-sf-group-ref") UUID group,
      @RequestBody ExpenseTypeDTO dto);
}
