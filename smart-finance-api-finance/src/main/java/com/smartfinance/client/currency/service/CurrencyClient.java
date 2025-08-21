package com.smartfinance.client.currency.service;

import com.smartfinance.client.currency.config.properties.CurrencyProperties;
import com.smartfinance.client.currency.exception.InternalServerErrorException;
import com.smartfinance.client.currency.model.Exchange;
import com.smartfinance.client.currency.request.CountriesDateRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyClient {
  private static final String ERROR = "Failed to load Exchanges for countries [ %s ]";

  private final CurrencyProperties properties;
  private final WebClient currency;

  @Cacheable(
      value = "exchange-from-countries-target",
      key = "#request.from + '_' + #request.target"
  )
  public List<Exchange> exchanges(CountriesDateRequest request) {
    return currency.post()
        .uri(properties.getResource().getCountriesDate())
        .bodyValue(request)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, resp
            -> Mono.error(new InternalServerErrorException(
                ERROR.formatted(Arrays.toString(request.getCountries().toArray())), resp)))
        .onStatus(HttpStatusCode::is5xxServerError, resp
            -> Mono.error(new InternalServerErrorException(
                ERROR.formatted(Arrays.toString(request.getCountries().toArray())), resp)))
        .bodyToMono(new ParameterizedTypeReference<List<Exchange>>() {})
        .block();
  }
}
