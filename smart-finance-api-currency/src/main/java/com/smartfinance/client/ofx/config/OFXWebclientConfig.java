package com.smartfinance.client.ofx.config;

import com.smartfinance.client.ofx.config.properties.OFXProperties;
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
public class OFXWebclientConfig {
  private static final int MB = 1024 * 1024;

  @Bean
  public WebClient ofx(OFXProperties properties) {
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
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * MB)) // 2MB
            .build())
        .build();
  }

  private ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      log.info("OFX {} - {}", clientRequest.method(), clientRequest.url());
      return Mono.just(clientRequest);
    });
  }

  private ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(response -> {
      log.info("OFX HTTP Status Code: {}", response.statusCode());
      return Mono.just(response);
    });
  }
}
