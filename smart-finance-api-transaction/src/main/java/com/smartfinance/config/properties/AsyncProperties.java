package com.smartfinance.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "exchange.schedule")
public class AsyncProperties {
  private String startDate = "2017-01-01";

  public LocalDate startDate() {
    return LocalDate.parse(startDate);
  }
}
