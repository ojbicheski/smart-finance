package com.smartfinance.customer.controller.v1;

import com.smartfinance.customer.controller.CommonController;
import com.smartfinance.customer.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface AccountTypeController extends CommonController {
  ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customerDTO);
  ResponseEntity<Void> del(@PathVariable UUID reference);
  ResponseEntity<CustomerDTO> get(@PathVariable UUID reference);
  Page<CustomerDTO> list(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "3") int size,
      @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction);
}
