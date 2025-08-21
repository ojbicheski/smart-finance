package com.smartfinance.finance.repository;

import com.smartfinance.finance.entity.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Expense, Long> {
}
