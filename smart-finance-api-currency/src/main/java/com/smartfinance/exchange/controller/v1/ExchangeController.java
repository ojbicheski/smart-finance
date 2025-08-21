package com.smartfinance.exchange.controller.v1;

import com.smartfinance.exchange.controller.v1.model.ExchangeRequest;
import com.smartfinance.exchange.dto.ExchangeDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ExchangeController {
  List<ExchangeDTO> exchanges(
      @RequestBody ExchangeRequest request);
}
