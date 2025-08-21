package com.smartfinance.operator.repository;

import com.smartfinance.operator.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Operator, Long> {
  @Query("select o from Operator o where o.country.reference = :country")
  List<Operator> findByCountry(UUID country);
}
