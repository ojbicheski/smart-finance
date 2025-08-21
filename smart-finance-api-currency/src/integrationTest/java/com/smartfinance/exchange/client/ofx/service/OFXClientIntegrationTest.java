package com.smartfinance.exchange.client.ofx.service;

import com.smartfinance.client.ofx.config.OFXWebclientConfig;
import com.smartfinance.client.ofx.config.properties.OFXProperties;
import com.smartfinance.client.ofx.exception.InternalServerErrorException;
import com.smartfinance.client.ofx.model.OFX;
import com.smartfinance.client.ofx.service.OFXClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class OFXClientIntegrationTest {
  private OFXProperties properties;

  private WebClient ofx;

  private OFXClient client;

  @BeforeEach
  void setup() {
    properties = new OFXProperties();
    properties.setInverted(new String[]{ "BRL", "MGA" });
    properties.setHost("https://api.ofx.com");

    OFXProperties.Resource resource = new OFXProperties.Resource();
    resource.setPeriod("/PublicSite.ApiService/SpotRateHistory/{period}/{from}/{to}?DecimalPlaces=6&ReportingInterval=daily&format=json");
    resource.setRange ("/PublicSite.ApiService/SpotRateHistory/{from}/{to}/{start}/{end}?DecimalPlaces=6&ReportingInterval=daily&format=json");
    properties.setResource(resource);

    OFXProperties.Timeout timeout = new OFXProperties.Timeout();
    timeout.setConnection(10000);
    timeout.setResponse(10000);
    timeout.setRead(5000);
    timeout.setWrite(5000);
    properties.setTimeout(timeout);

    ofx = new OFXWebclientConfig().ofx(properties);
    client = new OFXClient(properties, ofx);
  }

  @Test
  void shouldPeriodReturns200NotInverse() {
    // When
    // Then
    OFX ofx = client.period(OFXClient.Period.WEEK, "USD", "BRL");
    assertNotNull(ofx);
    assertFalse(ofx.isInverse());
    assertEquals(6, ofx.getHistorical().size());
  }

  @Test
  void shouldPeriodReturns200Inverse() {
    // When
    // Then
    OFX ofx = client.period(OFXClient.Period.WEEK, "BRL", "USD");
    assertNotNull(ofx);
    assertTrue(ofx.isInverse());
    assertEquals(6, ofx.getHistorical().size());
  }

  @Test
  void shouldRangeReturns200() {
    // When
    // Then
    OFX ofx = client.range("USD", "BRL", LocalDate.now().minusDays(7), LocalDate.now());
    assertNotNull(ofx);
    assertFalse(ofx.isInverse());
  }

  @Test
  void shouldRangeReturns4xx() {
    // When
    // Then
    assertThrows(InternalServerErrorException.class, () ->
        client.range("USA", "BRL", LocalDate.now().minusDays(7), LocalDate.now()));
  }
}
