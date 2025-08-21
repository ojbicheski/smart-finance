package com.smartfinance.transaction.repository;

import com.smartfinance.finance.entity.income.IncomeType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactRepository extends JpaRepository<IncomeType, Long> {
  default Optional<IncomeType> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            IncomeType.builder().reference(reference).build(),
            IncomeType.matcherRef
        )
    );
  }

  default List<IncomeType> search(IncomeType expenseType) {
    return this.findAll(Example.of(expenseType, expenseType.matcher()));
  }

  @Query("select it from IncomeType it where it.customer.reference = :customer and it.active = :active")
  List<IncomeType> findByCustomerAndActive(UUID customer, boolean active);
}
