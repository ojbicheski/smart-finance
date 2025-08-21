package com.smartfinance.preference.repository;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.preference.entity.Countries;
import com.smartfinance.shared.entity.Country;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long> {
  default Optional<Countries> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Countries.builder().reference(reference).build(),
            Countries.matcherRef
        )
    );
  }

  List<Countries> findByCustomer(Customer customer);

  void deleteByCustomer(Customer customer);
}
