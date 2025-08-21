package com.smartfinance.customer.controller.v1;

import com.smartfinance.customer.controller.CommonController;
import com.smartfinance.customer.dto.AccountDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface AccountController extends CommonController {
  ResponseEntity<AccountDTO> save(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @Valid @RequestBody AccountDTO dto);
  ResponseEntity<AccountDTO> get(@PathVariable UUID reference);
  ResponseEntity<Void> activate(@PathVariable UUID reference);
  ResponseEntity<Void> deactivate(@PathVariable UUID reference);
  ResponseEntity<Void> del(@PathVariable UUID reference);
  List<AccountDTO> list(
      @RequestHeader("x-sf-customer-ref") UUID customer,
      @RequestParam(defaultValue = "true") boolean active);
}
