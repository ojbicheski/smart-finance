package com.smartfinance.currency.repository;

import com.smartfinance.finance.entity.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Income, Long> {
}
