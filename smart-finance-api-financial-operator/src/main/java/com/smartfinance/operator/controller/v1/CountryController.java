package com.smartfinance.operator.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.operator.dto.OperatorDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

public interface CountryController extends CommonController {
  ResponseEntity<OperatorDTO> save(
      @RequestHeader("x-sf-country-ref") UUID country,
      @Valid @RequestBody OperatorDTO operatorDTO);
  ResponseEntity<OperatorDTO> get(
      @PathVariable("reference") UUID reference);
  ResponseEntity<Void> delete(
      @PathVariable("reference") UUID reference);
  List<OperatorDTO> list(
      @RequestHeader("x-sf-country-ref") UUID country);
}
