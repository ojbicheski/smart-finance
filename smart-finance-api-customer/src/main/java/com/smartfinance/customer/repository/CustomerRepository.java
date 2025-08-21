package com.smartfinance.customer.repository;

import com.smartfinance.customer.entity.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  default Optional<Customer> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Customer.builder().reference(reference).build(),
            Customer.matcherRef
        )
    );
  }

  Page<Customer> findAllByNameContaining(String name, Pageable pageable);
}
