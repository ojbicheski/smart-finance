package com.smartfinance.finance.service;

import com.smartfinance.finance.mq.model.ExpenseEvent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IncomeService {

  @Transactional
  public void create(ExpenseEvent event) {
  }
}
