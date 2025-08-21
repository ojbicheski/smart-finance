package com.smartfinance.loader.repository;

import com.smartfinance.loader.entity.Line;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Line, Long> {
  Optional<Line> findByReference(UUID reference);
  Page<Line> findAllByNameContaining(String name, Pageable pageable);
}
