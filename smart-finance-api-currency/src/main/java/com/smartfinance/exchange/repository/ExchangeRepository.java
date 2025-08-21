package com.smartfinance.exchange.repository;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.exchange.dto.ExchangeDTO;
import com.smartfinance.exchange.entities.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
  @Query("select max(e.exchanged) from Exchange e " +
      "where e.from.id = :from and e.to.id = :to")
  Optional<LocalDate> latestDate(long from, long to);

  @Query("select e from Exchange e " +
      "where e.exchanged = :target " +
      "and e.from = :from " +
      "and e.to in(:to)")
  List<Exchange> findTarget(LocalDate target, Currency from, List<Currency> to);
}
