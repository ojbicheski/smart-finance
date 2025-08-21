package com.smartfinance.operator.repository;

import com.smartfinance.operator.entity.Country;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.operator.exception.NotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
  default Optional<Country> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Country.builder().reference(reference).build(),
            Country.matcherRef
        )
    );
  }
}
