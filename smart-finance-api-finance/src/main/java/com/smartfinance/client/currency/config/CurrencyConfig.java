package com.smartfinance.client.currency.config;

import com.smartfinance.client.currency.config.properties.CurrencyProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class CurrencyConfig {
  private static final int MB = 1024 * 1024;

  @Bean
  public WebClient currency(CurrencyProperties properties) {
    HttpClient httpClient = HttpClient.create()
        .baseUrl(properties.getHost())
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getTimeout().getConnection())
        .responseTimeout(Duration.ofMillis(properties.getTimeout().getResponse()))
        .doOnConnected(conn -> conn
            .addHandlerLast(new ReadTimeoutHandler(
                properties.getTimeout().getRead(),
                TimeUnit.MILLISECONDS)
            )
            .addHandlerLast(new WriteTimeoutHandler(
                properties.getTimeout().getWrite(),
                TimeUnit.MILLISECONDS)
            )
        );

    return WebClient.builder()
        .filters(exchangeFilterFunctions -> {
          exchangeFilterFunctions.add(logRequest());
          exchangeFilterFunctions.add(logResponse());
        })
        .defaultHeaders(headers -> headers.setBasicAuth(
            properties.getAuth().getUser(),
            properties.getAuth().getPassword()))
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(MB)) // 1MB
            .build())
        .build();
  }

  private ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      log.info("Exchange {} - {}", clientRequest.method(), clientRequest.url());
      return Mono.just(clientRequest);
    });
  }

  private ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(response -> {
      log.info("Currency#Country#Exchange HTTP Status Code: {}", response.statusCode());
      return Mono.just(response);
    });
  }
}
