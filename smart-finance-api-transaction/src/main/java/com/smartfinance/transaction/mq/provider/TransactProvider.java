package com.smartfinance.transaction.mq.provider;

import com.smartfinance.transaction.mq.message.Builder;
import com.smartfinance.transaction.mq.model.FinanceEvent;
import com.smartfinance.transaction.mq.model.TransactEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.smartfinance.transaction.mq.config.RabbitMQConfig.*;

@Slf4j
@Component
@AllArgsConstructor
public class TransactProvider {
  private static final String CREDIT = "CREDIT";
  private final RabbitTemplate template;

  public void sendTransact(TransactEvent event) {
    log.info("Sending Transact Response [{}] Line [{}] by Account [{}]",
        event.getFile().getReference(),
        event.getLine().getNumber(),
        event.getFile().getAccount());
    template.convertAndSend(
        TRANSACT_LOADER_EXCHANGE,
        ROUTING_KEY_RESP_TRANSACT,
        Builder
            .message(event.bytes())
            .setHeader("x-sf-account-ref", event.getFile().getAccount())
            .build()
    );
    log.info("Transact Response sent");
  }

  public void sendFinance(FinanceEvent event, String type) {

    Message out = Builder
        .message(event.bytes())
        .setHeader("x-sf-account-ref", event.getAccount())
        .build();

    if (CREDIT.equalsIgnoreCase(type)) {
      template.convertAndSend(
          TRANSACT_FINANCE_EXCHANGE, ROUTING_KEY_TRANSACT_CREDIT_EVT, out);
      log.info("CREDIT transaction sent");
    } else {
      template.convertAndSend(
          TRANSACT_FINANCE_EXCHANGE, ROUTING_KEY_TRANSACT_DEBIT_EVT, out);
      log.info("DEBIT transaction sent");
    }
  }
}
