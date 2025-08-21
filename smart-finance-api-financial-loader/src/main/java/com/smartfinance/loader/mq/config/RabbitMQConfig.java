package com.smartfinance.loader.mq.config;

import brave.Tracing;
import brave.spring.rabbit.SpringRabbitTracing;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
  public static final String LOADER_EXCHANGE = "smart-finance.loader.exchange";
  public static final String LOADER_QUEUE = "smart-finance.loader.queue";
  public static final String ROUTING_KEY_TRIGGER_TRANSACT = "com.smart-finance.loader.trigger";

  public static final String LOADER_TRANSACT_EXCHANGE = "smart-finance.loader.transact.exchange";
  public static final String ROUTING_KEY_TRANSACT_EVT = "com.smart-finance.transact.event";

  public static final String TRANSACT_LOADER_EXCHANGE = "smart-finance.transact.loader.exchange";
  public static final String TRANSACT_LOADER_QUEUE = "smart-finance.transact.loader.queue";
  public static final String ROUTING_KEY_RESP_TRANSACT = "com.smart-finance.transact.response";

  @Bean
  public SpringRabbitTracing springRabbitTracing(Tracing tracing) {
    return SpringRabbitTracing
        .newBuilder(tracing)
        .build();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory,
      SpringRabbitTracing springRabbitTracing) {
    return springRabbitTracing.newRabbitTemplate(connectionFactory);
  }

  @Bean
  public ContainerCustomizer<SimpleMessageListenerContainer> containerCustomizer() {
    return container -> container.setObservationEnabled(true);
  }
}
