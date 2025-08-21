package com.smartfinance.exchange.client.ofx.service;

import com.smartfinance.client.ofx.config.properties.OFXProperties;
import com.smartfinance.client.ofx.model.OFX;
import com.smartfinance.client.ofx.service.OFXClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OFXClientTest {
  @Mock
  private WebClient webClient;
  @Mock
  private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
  @Mock
  private WebClient.RequestHeadersSpec requestHeadersSpec;
  @Mock
  private WebClient.ResponseSpec responseSpec;

  private OFXProperties properties;

  private OFXClient client;

  @BeforeEach
  void setup() {
    // Given
    properties = new OFXProperties();
    properties.setInverted(new String[]{ "BRL", "MGA" });
    properties.setHost("https://api.ofx.com");
    OFXProperties.Resource resource = new OFXProperties.Resource();
    resource.setPeriod("/PublicSite.ApiService/SpotRateHistory/{period}/{from}/{to}?DecimalPlaces=6&ReportingInterval=daily&format=json");
    resource.setRange("/PublicSite.ApiService/SpotRateHistory/{from}/{to}/{start}/{end}?DecimalPlaces=6&ReportingInterval=daily&format=json");
    properties.setResource(resource);
    // When
    when(webClient.get())
        .thenReturn(requestHeadersUriSpec);
    // Init client
    client = new OFXClient(properties, webClient);
  }

  @ParameterizedTest
  @EnumSource(OFXClient.Period.class)
  void shouldPeriodReturns200NotInverse(OFXClient.Period period) {
    // When
    when(requestHeadersUriSpec.uri(any(), any(), any(), any()))
        .thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve())
        .thenReturn(responseSpec);
    when(responseSpec.onStatus(any(), any()))
        .thenReturn(responseSpec);
    when(responseSpec.bodyToMono(ArgumentMatchers.<Class<OFX>>notNull()))
        .thenReturn(Mono.just(OFXUtil.ofx()));
    // Then
    OFX ofx = client.period(period, "USD", "BRL");
    assertNotNull(ofx);
    assertFalse(ofx.isInverse());
    assertTrue(ofx.stream().allMatch(historicalPoint -> historicalPoint.rate() > 4));
  }

  @ParameterizedTest
  @EnumSource(OFXClient.Period.class)
  void shouldPeriodReturns200Inverse(OFXClient.Period period) {
    // When
    when(requestHeadersUriSpec.uri(any(), any(), any(), any()))
        .thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve())
        .thenReturn(responseSpec);
    when(responseSpec.onStatus(any(), any()))
        .thenReturn(responseSpec);
    when(responseSpec.bodyToMono(ArgumentMatchers.<Class<OFX>>notNull()))
        .thenReturn(Mono.just(OFXUtil.ofx()));
    // Then
    OFX ofx = client.period(period, "BRL", "USD");
    assertNotNull(ofx);
    assertTrue(ofx.isInverse());
    assertTrue(ofx.stream().allMatch(historicalPoint -> historicalPoint.rate() < 4));
  }

  @ParameterizedTest
  @MethodSource("rangeReturns200")
  void shouldRangeReturns200(String from, String to, LocalDate start, LocalDate end, boolean inverseExpected) {
    // When
    when(requestHeadersUriSpec.uri(any(), any(), any(), any(), any()))
        .thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve())
        .thenReturn(responseSpec);
    when(responseSpec.onStatus(any(), any()))
        .thenReturn(responseSpec);
    when(responseSpec.bodyToMono(ArgumentMatchers.<Class<OFX>>notNull()))
        .thenReturn(Mono.just(OFXUtil.ofx()));
    // Then
    OFX ofx = client.range(from, to, start, end);
    assertNotNull(ofx);
    assertEquals(inverseExpected, ofx.isInverse());
  }
  private static Stream<Arguments> rangeReturns200() {
    return Stream.of(
        Arguments.of("USD", "BRL", LocalDate.now().minusDays(7), LocalDate.now(), false),
        Arguments.of("BRL", "USD", LocalDate.now().minusDays(7), LocalDate.now(), true)
    );
  }
}
