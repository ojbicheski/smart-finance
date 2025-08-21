package com.smartfinance.client.customer.service;

import com.smartfinance.client.customer.config.properties.CustomerProperties;
import com.smartfinance.client.customer.exception.InternalServerErrorException;
import com.smartfinance.client.customer.model.Country;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerClient {
  private static final String ERROR = "Failed to load Countries by Customer [ %s ]";

  private final CustomerProperties properties;
  private final WebClient customer;

  @Cacheable("customer-countries")
  public List<Country> countriesByCustomer(UUID customer) {
    return this.customer.get()
        .uri(properties.getResource().getCountries(), customer)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, resp
            -> Mono.error(new InternalServerErrorException(
                ERROR.formatted(customer), resp)))
        .onStatus(HttpStatusCode::is5xxServerError, resp
            -> Mono.error(new InternalServerErrorException(
                ERROR.formatted(customer), resp)))
        .bodyToMono(new ParameterizedTypeReference<List<Country>>() {})
        .block();
  }
}
