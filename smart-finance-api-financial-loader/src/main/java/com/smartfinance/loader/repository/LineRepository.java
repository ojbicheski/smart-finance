package com.smartfinance.loader.repository;

import com.smartfinance.loader.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LineRepository extends JpaRepository<File, Long> {
  Optional<File> findByReference(UUID reference);
  Page<File> findAllByNameContaining(String name, Pageable pageable);
}
