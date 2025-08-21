package com.smartfinance.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "cache.memory")
public class CacheProperties {
  private Map<String, Setup> cacheMap = new HashMap<>();

  @Setter
  @Getter
  public static class Setup {
    private int initialCapacity = 10; // initial capacity
    private int maximumSize = 100; // items stored
    private int expireAfterAccess = 5; // Timeout to remove item from cache
  }
}
