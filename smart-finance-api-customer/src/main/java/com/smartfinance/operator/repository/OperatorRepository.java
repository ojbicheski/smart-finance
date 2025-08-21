package com.smartfinance.operator.repository;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.operator.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
  Optional<Operator> findByReference(UUID reference);
}
