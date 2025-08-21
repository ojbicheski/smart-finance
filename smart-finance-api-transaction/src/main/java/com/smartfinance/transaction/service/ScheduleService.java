package com.smartfinance.transaction.service;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.currency.service.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleService {
  private final CurrencyService currencyService;
  private final ExchangeService exchangeService;

  @Scheduled(cron = "0 0/2 * * * ?")
  @SchedulerLock(
      name = "TaskScheduler_scheduledTask_exchange",
      lockAtLeastFor = "PT5M",
      lockAtMostFor = "PT15M"
  )
  public void run() {
    log.info("Schedule service triggered.");
    log.info("Processing Exchanges...");
    List<Currency> loop1 = currencyService.list();
    List<Currency> loop2 = loop1.stream().toList();

    loop1.forEach(from -> loop2.stream()
        .filter(to -> !to.getCode().equals(from.getCode()))
        .forEach(to -> exchangeService.loadExchanges(from, to))
    );
    log.info("Exchanges proceeded.");
  }
}
