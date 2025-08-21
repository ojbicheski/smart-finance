package com.smartfinance.preference.repository;

import com.smartfinance.shared.entity.Country;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountriesRepository extends JpaRepository<Country, Long> {
  default Optional<Country> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Country.builder().reference(reference).build(),
            Country.matcherRef
        )
    );
  }
}
