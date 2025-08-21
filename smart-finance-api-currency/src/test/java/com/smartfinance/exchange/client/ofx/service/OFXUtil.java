package com.smartfinance.exchange.client.ofx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfinance.client.ofx.model.OFX;
import lombok.SneakyThrows;

public class OFXUtil {

  @SneakyThrows
  public static OFX ofx() {
    return new ObjectMapper().readValue(
        OFXUtil.class.getResourceAsStream("/exchange/client/ofx/ofx-loader.json"),
        OFX.class
    );
  }
}
