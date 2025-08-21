package com.smartfinance.product.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.product.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface ProductController extends CommonController {
  ResponseEntity<ProductDTO> save(@RequestBody ProductDTO dto);
  ResponseEntity<ProductDTO> find(UUID reference);
  List<ProductDTO> list(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @RequestParam(defaultValue = "true") boolean active);
  ResponseEntity<Void> activate(UUID reference);
  ResponseEntity<Void> deactivate(UUID reference);
  ResponseEntity<Void> delete(UUID reference);
}
