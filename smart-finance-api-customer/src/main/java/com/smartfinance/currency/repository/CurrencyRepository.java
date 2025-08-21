package com.smartfinance.currency.repository;

import com.smartfinance.currency.entity.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
  Optional<Currency> findByReference(UUID reference);
  Page<Currency> findAllByNameContaining(String name, Pageable pageable);
}
