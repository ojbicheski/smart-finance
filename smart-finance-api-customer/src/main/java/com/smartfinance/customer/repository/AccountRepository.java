package com.smartfinance.customer.repository;

import com.smartfinance.customer.entity.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

  default List<Account> search(Account account) {
    return this.findAll(Example.of(account, account.matcher()));
  }

  @Query("select a from Account a where a.customer.reference = :customer and a.active = :active")
  List<Account> findByCustomerReferenceAndActive(UUID customer, boolean active);
}
