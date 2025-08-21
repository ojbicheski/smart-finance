package com.smartfinance.customer.controller.v1;

import com.smartfinance.customer.controller.CommonController;
import com.smartfinance.customer.dto.AccountTypeDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface AccountTypeController extends CommonController {
  ResponseEntity<AccountTypeDTO> save(@RequestBody AccountTypeDTO accountTypeDTO);
  ResponseEntity<Void> del(@PathVariable UUID reference);
  ResponseEntity<AccountTypeDTO> get(@PathVariable UUID reference);
  List<AccountTypeDTO> list();
}
