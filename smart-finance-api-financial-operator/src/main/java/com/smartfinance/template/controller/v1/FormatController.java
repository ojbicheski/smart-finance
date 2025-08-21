package com.smartfinance.template.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.template.dto.FormatDTO;
import com.smartfinance.template.dto.TemplateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface FormatController extends CommonController {
  ResponseEntity<FormatDTO> save(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @Valid @RequestBody FormatDTO format);
  ResponseEntity<FormatDTO> get(
      @PathVariable("reference") UUID reference);
  List<FormatDTO> list(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @RequestParam(name = "active", defaultValue = "true") boolean active);
  ResponseEntity<Void> activate(
      @PathVariable("reference") UUID reference);
  ResponseEntity<Void> deactivate(
      @PathVariable("reference") UUID reference);
  ResponseEntity<Void> delete(
      @PathVariable("reference") UUID reference);
}
