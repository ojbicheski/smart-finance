package com.smartfinance.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "async.executor")
public class AsyncProperties {
  private int maxPoolSize   = 10;
  private int corePoolSize  = 5;
  private int queueCapacity = 5;
}
