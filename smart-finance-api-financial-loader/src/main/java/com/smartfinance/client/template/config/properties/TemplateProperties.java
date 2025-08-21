package com.smartfinance.client.template.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "exchange.client.ofx")
public class TemplateProperties {
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
