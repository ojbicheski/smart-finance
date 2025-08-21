package com.smartfinance.finance.mq.consumer;

import com.smartfinance.finance.exception.InternalServerErrorException;
import com.smartfinance.finance.mq.message.Builder;
import com.smartfinance.finance.mq.model.ExpenseEvent;
import com.smartfinance.finance.mq.model.IncomeEvent;
import com.smartfinance.finance.service.ExpenseService;
import com.smartfinance.finance.service.IncomeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.smartfinance.finance.mq.config.RabbitMQConfig.*;

@Slf4j
@Component
@AllArgsConstructor
public class FinanceConsumer {
  private final ExpenseService expense;
  private final IncomeService income;

  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = TRANSACT_FINANCE_DEBIT_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"),
          exchange = @Exchange(value = TRANSACT_FINANCE_EXCHANGE),
          key = ROUTING_KEY_TRANSACT_DEBIT_EVT
      )
  )
  public void expense(Message in) {
    try {
      expense.create(Builder.messageToObject(in, ExpenseEvent.class));
    } catch (Exception e) {
      throw new InternalServerErrorException("Failed to load Expense message", e);
    }
  }

  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(value = TRANSACT_FINANCE_CREDIT_QUEUE, durable = "true", exclusive = "false", autoDelete = "false"),
          exchange = @Exchange(value = TRANSACT_FINANCE_EXCHANGE),
          key = ROUTING_KEY_TRANSACT_CREDIT_EVT
      )
  )
  public void income(Message in) {
    try {
      income.create(Builder.messageToObject(in, IncomeEvent.class));
    } catch (Exception e) {
      throw new InternalServerErrorException("Failed to load Income message", e);
    }
  }
}
