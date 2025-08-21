package com.smartfinance.finance.service;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.repository.CustomerRepository;
import com.smartfinance.finance.entity.expense.Expense;
import com.smartfinance.finance.exception.NotFoundException;
import com.smartfinance.finance.mq.model.ExpenseEvent;
import com.smartfinance.finance.repository.ExpenseRepository;
import com.smartfinance.transaction.entity.Transact;
import com.smartfinance.transaction.repository.TransactRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {
  private final ExpenseRepository repository;
  private final CustomerRepository customerRepository;
  private final TransactRepository transactRepository;

  @Transactional
  public void create(ExpenseEvent event) {
    Expense expense = Expense.builder()
        .customer(customer(event.getCustomer()))
        .transact(transact(event.getTransaction()))
        .build();
  }

  private Transact transact(UUID transaction) {
    return transactRepository.findByReference(transaction)
        .orElseThrow(() -> new NotFoundException("Transact not found"));
  }

  private Customer customer(UUID customer) {
    return customerRepository.findByReference(customer)
        .orElseThrow(() -> new NotFoundException("Customer not found"));
  }
}
