package com.smartfinance.transaction.mq.consumer;

import com.smartfinance.transaction.exception.InternalServerErrorException;
import com.smartfinance.transaction.mq.message.Builder;
import com.smartfinance.transaction.mq.model.TransactEvent;
import com.smartfinance.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.smartfinance.transaction.mq.config.RabbitMQConfig.*;

@Slf4j
@Component
@AllArgsConstructor
public class TransactConsumer {
  private final TransactionService service;
  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = LOADER_TRANSACT_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"),
          exchange = @Exchange(value = LOADER_TRANSACT_EXCHANGE),
          key = ROUTING_KEY_TRANSACT_EVT
      )
  )
  public void listen(Message in) {
    try {
      service.createEvent(Builder.messageToObject(in, TransactEvent.class));
    } catch (IOException e) {
      throw new InternalServerErrorException("Failed to load message Transact", e);
    }
  }
}
