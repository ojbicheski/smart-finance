package com.smartfinance.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
  private static final int MAX_POOL_SIZE = 10;
  private static final int CORE_POOL_SIZE = 8;
  private static final int QUEUE_CAPACITY = 8;
  public static final String EXEC_NAME = "Loader-Exec";

  @Bean(EXEC_NAME)
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setMaxPoolSize(MAX_POOL_SIZE);
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setQueueCapacity(QUEUE_CAPACITY);
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
