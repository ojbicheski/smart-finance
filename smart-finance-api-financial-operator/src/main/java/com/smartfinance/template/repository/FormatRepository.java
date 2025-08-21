package com.smartfinance.template.repository;

import com.smartfinance.template.entity.Format;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {
  default Optional<Format> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Format.builder().reference(reference).build(),
            Format.matcherRef
        )
    );
  }

  @Query("select f from Format f where f.operator.reference = :operator and f.active = :active")
  List<Format> findByOperatorAndActive(UUID operator, boolean active);
}
