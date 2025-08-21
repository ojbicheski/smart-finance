package com.smartfinance.product.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.product.dto.ProductDTO;
import com.smartfinance.product.dto.ProductDetailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface ProductDetailController extends CommonController {
  ResponseEntity<ProductDetailDTO> save(@RequestBody ProductDetailDTO dto);
  ResponseEntity<ProductDetailDTO> find(UUID reference);
  List<ProductDetailDTO> list(
      @RequestHeader("x-sf-product-ref") UUID product,
      @RequestParam(defaultValue = "true") boolean active);
  ResponseEntity<Void> activate(@PathVariable UUID reference);
  ResponseEntity<Void> deactivate(@PathVariable UUID reference);
  ResponseEntity<Void> delete(@PathVariable UUID reference);
}
