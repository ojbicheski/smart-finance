package com.smartfinance.loader.mq.config;

import brave.Tracing;
import brave.spring.rabbit.SpringRabbitTracing;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
  public static final String EXCHANGE_NAME = "smart-finance-loader-exchange";
  public static final String LOADER_Q_NAME = "smart-finance-loader";

  public static final String ROUTING_KEY_TRIGGER_TRANSACT = "com.smart-finance.loader.trigger.transact";

  @Bean
  public Queue queue() {
    return new Queue(LOADER_Q_NAME, true);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(EXCHANGE_NAME, true, false);
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder
        .bind(queue)
        .to(exchange)
        .with("com.smart-finance.loader.#");
  }

  @Bean
  public SpringRabbitTracing springRabbitTracing(Tracing tracing) {
    return SpringRabbitTracing
        .newBuilder(tracing)
        .build();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, SpringRabbitTracing tracing) {
    return tracing.newRabbitTemplate(connectionFactory);
  }

  @Bean
  public ContainerCustomizer<SimpleMessageListenerContainer> containerCustomizer() {
    return container -> container.setObservationEnabled(true);
  }
}
