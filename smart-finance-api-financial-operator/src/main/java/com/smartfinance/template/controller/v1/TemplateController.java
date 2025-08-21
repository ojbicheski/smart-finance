package com.smartfinance.template.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.template.dto.TemplateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface TemplateController extends CommonController {
  ResponseEntity<TemplateDTO> save(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @RequestBody TemplateDTO template);
  ResponseEntity<TemplateDTO> get(UUID reference);
  ResponseEntity<TemplateDTO> getByOperator(UUID operator);
  List<TemplateDTO> list(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @RequestParam(defaultValue = "true") boolean active);
  ResponseEntity<Void> activate(UUID reference);
  ResponseEntity<Void> deactivate(UUID reference);
  ResponseEntity<Void> delete(UUID reference);
}
