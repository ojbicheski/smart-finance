package com.smartfinance.shared.repository;

import com.smartfinance.currency.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
  Optional<Country> findByReference(UUID reference);
  Optional<Country> findByCode(String code);
}
