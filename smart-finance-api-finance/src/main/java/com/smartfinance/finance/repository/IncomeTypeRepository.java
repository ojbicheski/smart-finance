package com.smartfinance.finance.repository;

import com.smartfinance.finance.entity.expense.ExpenseType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeTypeRepository extends JpaRepository<ExpenseType, Long> {
  default Optional<ExpenseType> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            ExpenseType.builder().reference(reference).build(),
            ExpenseType.matcherRef
        )
    );
  }

  default List<ExpenseType> search(ExpenseType expenseType) {
    return this.findAll(Example.of(expenseType, expenseType.matcher()));
  }

  @Query("select et from ExpenseType et where et.group.reference = :group and et.active = :active")
  List<ExpenseType> findByGroupAndActive(UUID group, boolean active);
}
