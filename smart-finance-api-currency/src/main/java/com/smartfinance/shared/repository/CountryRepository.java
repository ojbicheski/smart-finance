package com.smartfinance.shared.repository;

import com.smartfinance.shared.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
  Optional<Country> findByCode(String code);
}
