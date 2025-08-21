package com.smartfinance.transaction.service;

import com.smartfinance.customer.entity.Account;
import com.smartfinance.customer.repository.AccountRepository;
import com.smartfinance.transaction.entity.Transact;
import com.smartfinance.transaction.exception.BadRequestException;
import com.smartfinance.transaction.exception.NotFoundException;
import com.smartfinance.transaction.mq.model.FinanceEvent;
import com.smartfinance.transaction.mq.model.TransactEvent;
import com.smartfinance.transaction.mq.provider.TransactProvider;
import com.smartfinance.transaction.repository.TransactRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionService {
  private final TransactRepository repository;
  private final AccountRepository accountRepository;

  private final TransactProvider provider;

  @Transactional
  public void createEvent(TransactEvent event) {
    if (event.getEvent() == null) {
      throw new BadRequestException("Event is invalid");
    }

    try {
      Account account = account(event.getFile().getAccount());
      Transact transact = Transact.builder()
          .account(account)
          .currency(account.getCurrency())
          .event(event.getEvent())
          .build();
      
      if (isNotDuplicated(transact)) {
        event.setResponse(response("LOADED"));
        transact = repository.saveAndFlush(transact);

        provider.sendFinance(
            FinanceEvent.builder()
                .customer(account.getCustomer().getReference())
                .account(account.getReference())
                .transaction(transact.getId())
                .build(),
            transact.getType().name()
        );
      } else {
        event.setResponse(response("DUPLICATED"));
      }
    } catch (Exception e) {
      log.error("Failed to save Transact", e);
      event.setResponse(response("FAIL_TO_LOAD", e.getMessage()));
    }

    provider.sendTransact(event);
  }

  private Account account(UUID account) {
    return accountRepository.findByReference(account)
        .orElseThrow(() -> new NotFoundException("Not found Account [%s]".formatted(account)));
  }

  private boolean isNotDuplicated(Transact transact) {
    return repository.findDuplicated(transact).stream()
        .noneMatch(t -> t.getAmount() == transact.getAmount());
  }

  private TransactEvent.ResponseTransact response(String status, String...message) {
    return switch (status) {
      case "LOADED" ->
          TransactEvent.ResponseTransact.builder()
            .status("LOADED")
            .message("File line converted to Transact.")
            .build();
      case "FAIL_TO_LOAD" ->
          TransactEvent.ResponseTransact.builder()
              .status("FAIL_TO_LOAD")
              .message(message[0])
              .build();
      default -> TransactEvent.ResponseTransact.builder()
          .status("DUPLICATED")
          .message("Transaction is duplicated")
          .build();
    };
  }
}
