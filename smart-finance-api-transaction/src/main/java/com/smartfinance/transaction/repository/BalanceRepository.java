package com.smartfinance.transaction.repository;

import com.smartfinance.transaction.entity.Balance;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
  default Optional<Balance> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Balance.builder().reference(reference).build(),
            Balance.matcherRef
        )
    );
  }

//  @Query("SELECT b FROM Balance b " +
//      "WHERE b.account.id = :accountId " +
//      "ORDER BY DESC b.year, b.month " +
//      "FETCH FIRST 1 ROWS ONLY")
//  Optional<Balance> findByAccount(long accountId);
  Optional<Balance> findTopByAccountIdOrderByYearDescMonthDesc(long accountId);
}
