package com.smartfinance.customer.repository;

import com.smartfinance.customer.entity.AccountType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
  default Optional<AccountType> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            AccountType.builder().reference(reference).build(),
            AccountType.matcherRef
        )
    );
  }
}
