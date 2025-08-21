package com.smartfinance.loader.mq.provider;

import com.smartfinance.loader.entity.File;
import com.smartfinance.loader.mq.message.Builder;
import com.smartfinance.loader.mq.model.Transact;
import com.smartfinance.loader.mq.model.TriggerTransact;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.smartfinance.loader.mq.config.RabbitMQConfig.*;

@Slf4j
@Component
@AllArgsConstructor
public class LoaderProvider {
  private final RabbitTemplate template;

  public void triggerTransact(File file, UUID template) {
    log.info("Sending Trigger Transact File [{}] by Account [{}]",
        file.getReference(), file.getAccount().getReference());
    this.template.convertAndSend(
        LOADER_EXCHANGE,
        ROUTING_KEY_TRIGGER_TRANSACT,
        Builder
            .message(TriggerTransact.builder()
                .account(file.getAccount().getReference().toString())
                .file(file.getReference().toString())
                .fileName(file.getFileName())
                .template(template.toString())
                .totalLines(Optional.ofNullable(file.getLines())
                    .map(List::size)
                    .orElse(0))
                .build().bytes()
            )
            .setHeader("x-sf-account-ref", file.getAccount().getReference())
            .build()
    );
    log.info("Trigger Transact sent");
  }

  public void sendTransact(Transact transact) {
    log.info("Sending Transact File [{}] Line [{}] by Account [{}]",
        transact.getFile().getReference(),
        transact.getLine().getNumber(),
        transact.getFile().getAccount());
    template.convertAndSend(
        LOADER_TRANSACT_EXCHANGE,
        ROUTING_KEY_TRANSACT_EVT,
        Builder
            .message(transact.bytes())
            .setHeader("x-sf-account-ref", transact.getFile().getAccount())
            .build()
    );
    log.info("Transact sent");
  }
}
