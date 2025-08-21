package com.smartfinance.finance.service;

import com.smartfinance.client.currency.model.Exchange;
import com.smartfinance.client.currency.service.CurrencyClient;
import com.smartfinance.client.customer.service.CustomerClient;
import com.smartfinance.currency.repository.CurrencyRepository;
import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.repository.CustomerRepository;
import com.smartfinance.exchange.repository.ExchangeRepository;
import com.smartfinance.finance.entity.income.Income;
import com.smartfinance.finance.entity.income.IncomeValue;
import com.smartfinance.finance.mq.model.IncomeEvent;
import com.smartfinance.finance.repository.IncomeRepository;
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
public class IncomeService extends EventService<IncomeEvent> {
  private final IncomeRepository repository;

  public IncomeService(IncomeRepository repository, CustomerRepository customerRepository,
                       TransactRepository transactRepository, CustomerClient customerClient,
                       CurrencyClient currencyClient, CurrencyRepository currencyRepository, ExchangeRepository exchangeRepository) {
    super(customerRepository, transactRepository, customerClient, currencyClient,
        currencyRepository, exchangeRepository);
    this.repository = repository;
  }

  @Transactional
  public void create(IncomeEvent event) {
    Transact transact = transact(event.getTransaction());
    if (TransactType.DEBIT.equals(transact.getType())) {
      log.warn("Transact {} was discarded. It is a DEBIT transaction.",
          transact.getReference());
      return;
    }
    Customer customer = customer(event.getCustomer());

    List<Exchange> exchanges = exchanges(customer, transact);

    Income income = repository.saveAndFlush(Income.builder()
        .customer(customer(event.getCustomer()))
        .transact(transact)
        .values(values(transact, exchanges))
        .build()
    );
    log.debug("Created income to Transact {}. Income: {}",
        transact.getReference(), income);
  }

  private List<IncomeValue> values(Transact transact, List<Exchange> exchanges) {
    List<IncomeValue> values = new ArrayList<>();
    exchanges.forEach(exchange ->
        values.add(IncomeValue.builder()
            .amount(transact.getAmount() * exchange.getValue())
            .currency(currency(exchange.getTo().getReference()))
            .exchange(exchange(exchange.getReference()))
            .build())
    );

    values.add(IncomeValue.builder()
        .amount(transact.getAmount())
        .currency(transact.getCurrency())
        .build());

    return values;
  }
}
