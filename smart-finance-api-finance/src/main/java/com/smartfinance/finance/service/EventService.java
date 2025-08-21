package com.smartfinance.finance.service;

import com.smartfinance.client.currency.model.Exchange;
import com.smartfinance.client.currency.request.CountriesDateRequest;
import com.smartfinance.client.currency.service.CurrencyClient;
import com.smartfinance.client.customer.model.Country;
import com.smartfinance.client.customer.service.CustomerClient;
import com.smartfinance.currency.entity.Currency;
import com.smartfinance.currency.repository.CurrencyRepository;
import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.repository.CustomerRepository;
import com.smartfinance.exchange.repository.ExchangeRepository;
import com.smartfinance.finance.exception.NotFoundException;
import com.smartfinance.transaction.entity.Transact;
import com.smartfinance.transaction.repository.TransactRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public abstract class EventService<T> {
  protected final CustomerRepository customerRepository;
  protected final TransactRepository transactRepository;
  protected final CustomerClient customerClient;
  protected final CurrencyClient currencyClient;
  private final CurrencyRepository currencyRepository;
  private final ExchangeRepository exchangeRepository;

  protected Transact transact(long transaction) {
    return transactRepository.findById(transaction)
        .orElseThrow(() -> new NotFoundException("Transact not found"));
  }

  protected Customer customer(UUID customer) {
    return customerRepository.findByReference(customer)
        .orElseThrow(() -> new NotFoundException("Customer not found"));
  }

  protected List<Exchange> exchanges(Customer customer, Transact transact) {
    List<UUID> countries = new ArrayList<>();
    customerClient.countriesByCustomer(customer.getReference()).stream()
        .map(Country::getReference)
        .forEach(countries::add);

    return currencyClient.exchanges(CountriesDateRequest.builder()
            .from(transact.getCurrency().getReference())
            .countries(countries)
            .target(transact.getDate())
        .build()
    );
  }

  protected Currency currency(UUID reference) {
    return currencyRepository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Currency not found"));
  }

  protected com.smartfinance.exchange.entity.Exchange exchange(UUID reference) {
    return exchangeRepository.findByReference(reference)
        .orElseThrow(() -> new NotFoundException("Exchange not found"));
  }

  public abstract void create(T event);
}
