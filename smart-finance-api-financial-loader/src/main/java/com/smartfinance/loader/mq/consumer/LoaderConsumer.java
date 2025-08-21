package com.smartfinance.loader.mq.consumer;

import com.smartfinance.loader.exception.InternalServerErrorException;
import com.smartfinance.loader.mq.message.Builder;
import com.smartfinance.loader.mq.model.Transact;
import com.smartfinance.loader.mq.model.TriggerTransact;
import com.smartfinance.loader.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.smartfinance.loader.mq.config.RabbitMQConfig.*;

@Slf4j
@Component
@AllArgsConstructor
public class LoaderConsumer {
  private final FileService service;

  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = LOADER_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"),
          exchange = @Exchange(value = LOADER_EXCHANGE),
          key = ROUTING_KEY_TRIGGER_TRANSACT
      )
  )
  public void trigger(Message in) {
    try {
      service.trigger(Builder.messageToObject(in, TriggerTransact.class));
    } catch (IOException e) {
      throw new InternalServerErrorException("Failed to load message TriggerTransact", e);
    }
  }

  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = TRANSACT_LOADER_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"),
          exchange = @Exchange(value = TRANSACT_LOADER_EXCHANGE),
          key = ROUTING_KEY_RESP_TRANSACT
      )
  )
  public void transactResponse(Message in) {
    try {
      Transact transact = Builder.messageToObject(in, Transact.class);
      service.response(transact);
      log.info("Line transact completed. Transact: {}", transact);
    } catch (IOException e) {
      throw new InternalServerErrorException("Failed to load message TriggerTransact response", e);
    }
  }
}
