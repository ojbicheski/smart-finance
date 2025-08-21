package com.smartfinance.transaction.service;

import com.smartfinance.config.AsyncConfig;
import com.smartfinance.customer.entity.Account;
import com.smartfinance.customer.entity.Customer;
import com.smartfinance.customer.repository.AccountRepository;
import com.smartfinance.transaction.entity.Balance;
import com.smartfinance.transaction.entity.Transact;
import com.smartfinance.transaction.repository.BalanceRepository;
import com.smartfinance.transaction.repository.TransactRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class BalanceService {
  private final AccountRepository accountRepository;
  private final TransactRepository transactRepository;
  private final BalanceRepository repository;

  @Async(AsyncConfig.EXEC_NAME)
  @Transactional
  public void update(Customer customer) {
    log.info("Updating balance for Customer {}",
        customer.getReference());
    accountRepository.findByCustomer(customer).forEach(this::updateAccount);
    log.info("Customer {} has all accounts up-to-date.",
        customer.getReference());
  }

  private void updateAccount(Account account) {
    log.info("Updating balance for account {}", account.getReference());
    transactRepository.transactionsToUpdateBalance(account)
        .forEach(this::balance);
    log.info("Account {} is up-to-date.", account.getReference());
  }

  private void balance(Transact transact) {
    log.info("Balancing transaction {}", transact.getReference());
    Balance balance = repository.findTopByAccountIdOrderByYearDescMonthDesc(transact.getAccount().getId())
        .orElse(Balance.builder()
            .account(transact.getAccount())
            .currency(transact.getCurrency())
            .year(transact.getYear())
            .month(transact.getMonth())
            .amount(0f)
            .build());

    Balance entity = balance;
    if (transact.getTarget() > balance.getTarget()) {
      repository.saveAndFlush(balance.close());
      entity = repository.saveAndFlush(Balance.builder()
          .account(transact.getAccount())
          .currency(transact.getCurrency())
          .year(transact.getYear())
          .month(transact.getMonth())
          .amount(balance.getAmount())
          .build()
          .add(transact.signedAmount()));
    } else {
      repository.saveAndFlush(
          balance.add(transact.signedAmount())
      );
    }

    transact.setBalanced(true);
    transactRepository.saveAndFlush(transact);

    log.debug("Balance {}", entity);
    log.info("Transaction {} balanced", transact.getReference());
  }
}
