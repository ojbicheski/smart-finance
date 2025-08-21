package com.smartfinance.loader.repository;

import com.smartfinance.loader.entity.File;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
  default Optional<File> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            File.builder().reference(reference).build(),
            File.matcherRef
        )
    );
  }
  Page<File> findAllByFileNameContaining(String fileName, Pageable pageable);
}
