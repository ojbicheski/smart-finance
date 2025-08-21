package com.smartfinance.config;

import com.smartfinance.config.properties.AsyncProperties;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@AllArgsConstructor
public class AsyncConfig implements AsyncConfigurer {
  public static final String EXEC_NAME = "Transaction-Exec";

  private final AsyncProperties properties;

  @Bean(EXEC_NAME)
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setMaxPoolSize(properties.getMaxPoolSize());
    executor.setCorePoolSize(properties.getCorePoolSize());
    executor.setQueueCapacity(properties.getQueueCapacity());
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setThreadNamePrefix(EXEC_NAME);
    executor.setTaskDecorator(runnable -> {
        final var mdcContextMap = MDC.getCopyOfContextMap();
        return () -> {
          try {
            if (mdcContextMap != null) {
              MDC.setContextMap(mdcContextMap);
            }
            runnable.run();
          } finally {
            MDC.clear();
          }
        };
    });
    executor.initialize();
    return executor;
  }
}
