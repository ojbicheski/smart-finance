package com.smartfinance.finance.service;

import com.smartfinance.client.currency.model.Exchange;
import com.smartfinance.client.currency.service.CurrencyClient;
import com.smartfinance.client.customer.service.CustomerClient;
import com.smartfinance.currency.repository.CurrencyRepository;
import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.repository.CustomerRepository;
import com.smartfinance.exchange.repository.ExchangeRepository;
import com.smartfinance.finance.entity.expense.Expense;
import com.smartfinance.finance.entity.expense.ExpenseValue;
import com.smartfinance.finance.mq.model.ExpenseEvent;
import com.smartfinance.finance.repository.ExpenseRepository;
import com.smartfinance.transaction.entity.Transact;
import com.smartfinance.transaction.entity.TransactType;
import com.smartfinance.transaction.repository.TransactRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExpenseService extends EventService<ExpenseEvent> {
  private final ExpenseRepository repository;

  public ExpenseService(ExpenseRepository repository, CustomerRepository customerRepository,
                        TransactRepository transactRepository, CustomerClient customerClient,
                        CurrencyClient currencyClient, CurrencyRepository currencyRepository, ExchangeRepository exchangeRepository) {
    super(customerRepository, transactRepository, customerClient, currencyClient,
        currencyRepository, exchangeRepository);
    this.repository = repository;
  }

  @Transactional
  public void create(ExpenseEvent event) {
    Transact transact = transact(event.getTransaction());
    if (TransactType.CREDIT.equals(transact.getType())) {
      log.warn("Transact {} was discarded. It is a CREDIT transaction.",
          transact.getReference());
      return;
    }
    Customer customer = customer(event.getCustomer());

    List<Exchange> exchanges = exchanges(customer, transact);

    Expense expense = repository.saveAndFlush(Expense.builder()
        .customer(customer)
        .transact(transact)
        .values(values(transact, exchanges))
        .build()
    );
    log.debug("Created expense to Transact {}. Expense: {}",
        transact.getReference(), expense);
  }

  private List<ExpenseValue> values(Transact transact, List<Exchange> exchanges) {
    List<ExpenseValue> values = new ArrayList<>();
    exchanges.forEach(exchange ->
      values.add(ExpenseValue.builder()
          .amount(transact.getAmount() * exchange.getValue())
          .currency(currency(exchange.getTo().getReference()))
          .exchange(exchange(exchange.getReference()))
          .build())
    );

    values.add(ExpenseValue.builder()
        .amount(transact.getAmount())
        .currency(transact.getCurrency())
        .build());

    return values;
  }
}
