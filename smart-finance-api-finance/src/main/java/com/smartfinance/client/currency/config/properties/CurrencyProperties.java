package com.smartfinance.client.currency.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "client.customer")
public class CurrencyProperties {
  private String host;

  private Resource resource;
  @Setter
  @Getter
  public static class Resource {
    private String countriesDate;
  }

  private BasicAuth auth;
  @Setter
  @Getter
  public static class BasicAuth {
    private String user;
    private String password;
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
