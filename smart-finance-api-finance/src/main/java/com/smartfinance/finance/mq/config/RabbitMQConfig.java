package com.smartfinance.finance.mq.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
  public static final String TRANSACT_FINANCE_EXCHANGE = "smart-finance.transact.finance.exchange";
  public static final String TRANSACT_FINANCE_DEBIT_QUEUE = "smart-finance.transact.finance.debit.queue";
  public static final String ROUTING_KEY_TRANSACT_DEBIT_EVT = "com.smart-finance.transact.debit.event";
  public static final String TRANSACT_FINANCE_CREDIT_QUEUE = "smart-finance.transact.finance.credit.queue";
  public static final String ROUTING_KEY_TRANSACT_CREDIT_EVT = "com.smart-finance.transact.credit.event";

  @Bean
  public ContainerCustomizer<SimpleMessageListenerContainer> containerCustomizer() {
    return container -> container.setObservationEnabled(true);
  }
}
