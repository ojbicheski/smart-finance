package com.smartfinance.currency.repository;

import com.smartfinance.currency.entity.Currency;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
  default Optional<Currency> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Currency.builder().reference(reference).build(),
            Currency.matcherRef
        )
    );
  }
}
