package com.smartfinance.transaction.repository;

import com.smartfinance.transaction.entity.Transact;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactRepository extends JpaRepository<Transact, Long> {
  default Optional<Transact> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Transact.builder().reference(reference).build(),
            Transact.matcherRef
        )
    );
  }
}
