package com.smartfinance.loader.repository;

import com.smartfinance.customer.entity.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  default Optional<Account> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Account.builder().reference(reference).build(),
            Account.matcherRef
        )
    );
  }
}
