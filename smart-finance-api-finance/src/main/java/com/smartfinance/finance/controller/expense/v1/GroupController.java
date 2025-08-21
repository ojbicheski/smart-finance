package com.smartfinance.finance.controller.expense.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.finance.dto.expense.GroupDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface GroupController extends CommonController {
  ResponseEntity<GroupDTO> save(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @Valid @RequestBody GroupDTO dto);
  ResponseEntity<GroupDTO> find(
      @PathVariable("reference") UUID reference);
  ResponseEntity<Void> delete(
      @PathVariable("reference") UUID reference);
  ResponseEntity<GroupDTO> activate(
      @PathVariable("reference") UUID reference);
  ResponseEntity<GroupDTO> deactivate(
      @PathVariable("reference") UUID reference);
  List<GroupDTO> list(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @RequestParam(defaultValue = "true") boolean active);
  List<GroupDTO> search(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @RequestBody GroupDTO dto);
}
