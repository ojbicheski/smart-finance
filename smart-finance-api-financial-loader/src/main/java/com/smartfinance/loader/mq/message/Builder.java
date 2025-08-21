package com.smartfinance.loader.mq.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageBuilderSupport;

import java.io.IOException;

@Slf4j
public class Builder {
  public static MessageBuilderSupport<Message> message(byte[] body) {
    return MessageBuilder
        .withBody(body)
        .setHeader("traceId", MDC.get("traceId"))
        .setHeader("spanId", MDC.get("spanId"));
  }

  public static <T> T messageToObject(Message message, Class<T> klass)
      throws IOException {
    log.debug("Message : {}", new String(message.getBody()));

    message.getMessageProperties().getHeaders().entrySet().stream()
        .filter(p -> p.getValue() instanceof String)
        .forEach(entry -> MDC.put(entry.getKey(), entry.getValue().toString()));

    log.info("Message received");
    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JavaTimeModule());
    return om.readValue(message.getBody(), klass);
  }
}
