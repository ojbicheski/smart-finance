package com.smartfinance.finance.mq.message;

import com.smartfinance.config.JacksonConfig;
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
    return new JacksonConfig().objectMapper()
        .readValue(message.getBody(), klass);
  }
}
