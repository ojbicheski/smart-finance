package com.smartfinance.product.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.product.dto.TypeDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface TypeController extends CommonController {
  ResponseEntity<TypeDTO> save(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @Valid @RequestBody TypeDTO dto);
  ResponseEntity<TypeDTO> find(
      @PathVariable("reference") UUID reference);
  ResponseEntity<Void> delete(
      @PathVariable("reference") UUID reference);
  List<TypeDTO> list(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @RequestParam(defaultValue = "true") boolean active);
}
