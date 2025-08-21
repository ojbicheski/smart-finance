package com.smartfinance.exchange.repository;

import com.smartfinance.exchange.entity.Exchange;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
  default Optional<Exchange> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Exchange.builder().reference(reference).build(),
            Exchange.matcherRef
        )
    );
  }
}
