package com.smartfinance.operator.controller.v1;

import com.smartfinance.controller.CommonController;
import com.smartfinance.operator.dto.OperatorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface OperatorController extends CommonController {
  ResponseEntity<Void> save(@RequestBody OperatorDTO operatorDTO);
  ResponseEntity<Void> delete(UUID reference);
  ResponseEntity<OperatorDTO> find(UUID reference);
  List<OperatorDTO> list(@RequestParam UUID country);
}
