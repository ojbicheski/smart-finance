package com.smartfinance.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.smartfinance.config.properties.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
  @Bean
  public CacheManager cacheManager(CacheProperties properties) {
    CaffeineCacheManager manager = new CaffeineCacheManager();

    properties.getCacheMap().forEach((key, value) ->
        manager.registerCustomCache(key, Caffeine.newBuilder()
            .initialCapacity(value.getInitialCapacity())
            .maximumSize(value.getMaximumSize())
            .expireAfterAccess(value.getExpireAfterAccess(), TimeUnit.MINUTES)
            .build())
    );

    return manager;
  }
}
