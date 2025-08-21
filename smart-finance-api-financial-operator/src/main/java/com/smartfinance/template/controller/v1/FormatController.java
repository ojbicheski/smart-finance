package com.smartfinance.template.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.template.dto.TemplateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface FormatController extends CommonController {
  ResponseEntity<TemplateDTO> save(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @RequestBody TemplateDTO template);
  ResponseEntity<TemplateDTO> get(UUID reference);
  List<TemplateDTO> list(
      @RequestHeader("x-sf-operator-ref") UUID operator,
      @RequestParam(defaultValue = "false") boolean active);
  ResponseEntity<TemplateDTO> activate(UUID reference);
  ResponseEntity<TemplateDTO> deactivate(UUID reference);
}
