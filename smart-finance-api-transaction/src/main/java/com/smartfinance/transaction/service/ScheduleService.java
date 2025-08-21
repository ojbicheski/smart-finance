package com.smartfinance.transaction.service;

import com.smartfinance.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleService {
  private final CustomerRepository customerRepository;
  private final BalanceService service;

  @Scheduled(cron = "${cron.job.update-balances:* */10 * * * *}")
  @SchedulerLock(
      name = "TaskScheduler_scheduledTask_balance",
      lockAtLeastFor = "PT5M",
      lockAtMostFor = "PT20M"
  )
  public void updateBalance() {
    log.info("Schedule service triggered.");
    log.info("Processing Balances...");
    customerRepository.findAll().forEach(service::update);
    log.info("Balances proceeded.");
  }
}
