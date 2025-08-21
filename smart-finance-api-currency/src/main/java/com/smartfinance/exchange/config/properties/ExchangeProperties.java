package com.smartfinance.exchange.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.stream.Stream;

@ConfigurationProperties(prefix = "exchange.client.ofx", ignoreUnknownFields = false)
@Setter
@Getter
public class ExchangeProperties {
//  private String[] periods;
  private String[] inverted;
  private String host;

  public boolean isInverted(String currency) {
    return Stream.of(inverted).anyMatch(i -> i.equalsIgnoreCase(currency));
  }

  private Resource resource;
  @Setter
  @Getter
  public static class Resource {
    private String period;
    private String range;
  }

  private Timeout timeout;
  @Setter
  @Getter
  public static class Timeout {
    private int connection;
    private int response;
    private int read;
    private int write;
  }
}
