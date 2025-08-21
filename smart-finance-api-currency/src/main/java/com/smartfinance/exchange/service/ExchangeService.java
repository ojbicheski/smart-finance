package com.smartfinance.exchange.service;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.client.ofx.model.OFX;
import com.smartfinance.client.ofx.service.OFXClient;
import com.smartfinance.config.AsyncConfig;
import com.smartfinance.currency.service.CurrencyService;
import com.smartfinance.exchange.config.properties.ExchangeProperties;
import com.smartfinance.exchange.dto.ExchangeDTO;
import com.smartfinance.exchange.entities.Exchange;
import com.smartfinance.exchange.repository.ExchangeRepository;
import com.smartfinance.mapper.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
@Slf4j
public class ExchangeService {
  private final ExchangeProperties properties;
  private final ExchangeRepository repository;
  private final CurrencyService currencyService;
  private final OFXClient ofxClient;
  private final Mapper<Exchange, ExchangeDTO> exchangeMapper;

  @Async(AsyncConfig.EXEC_NAME)
  @Transactional
  public void loadExchanges(Currency from, Currency to) {
    LocalDate start = repository.latestDate(from.getId(), to.getId())
        .map(dt -> dt.plusDays(1))
        .orElse(properties.startDate());
    LocalDate end = LocalDate.now();

    log.info("From {} To {} - Start: {} End: {}  Loading exchanges...",
        from.getCode(), to.getCode(), start, end);

    if (ChronoUnit.DAYS.between(start, end) < 1) {
      log.warn("Skip Exchange. Diff between start and end date is lower than 1 day.");
      return;
    }

    OFX ofx = ofxClient.range(from.getCode(), to.getCode(), start, end);

    List<Exchange> exchanges = repository.saveAllAndFlush(ofx.stream()
        .map(hp -> Exchange.builder()
            .exchanged(hp.getPointInTime())
            .from(from)
            .to(to)
            .value(hp.rate())
            .build())
        .toList()
    );

    log.info("From {} To {} - Loaded {} records.", from.getCode(), to.getCode(), exchanges.size());
    if (log.isTraceEnabled()) {
      AtomicInteger count = new AtomicInteger(1);
      exchanges.forEach(ex ->
          log.trace("({}) Exchange -> {}", count.getAndIncrement(), ex));
    }
  }

  public List<ExchangeDTO> exchanges(UUID currencyFrom, List<UUID> countries, LocalDate targetDate) {
    Currency from = currencyService.get(currencyFrom);
    List<Currency> to = currencyService.listByCountries(countries);

    return exchangesByTargetAndCurrency(targetDate, from, to, 0).stream()
        .map(exchangeMapper::toDto)
        .toList();
  }

  private List<Exchange> exchangesByTargetAndCurrency(LocalDate targetDate, Currency from, List<Currency> to, int times) {
    log.debug("Exchange By Target. Times: {}", times);
    if (times >= 5) {
      log.warn("Exchange By Target exceeded the limit Times [{}]. " +
          "Check if the Exchange base is properly load.", times);
      return List.of();
    }

    List<Exchange> exchanges = repository.findTarget(targetDate, from, to);
    if (exchanges.isEmpty()) {
      return exchangesByTargetAndCurrency(targetDate.minusDays(1), from, to, ++times);
    }

    return exchanges;
  }
}
