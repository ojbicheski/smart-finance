package com.smartfinance.product.repository;

import com.smartfinance.product.entity.Type;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
  default Optional<Type> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Type.builder().reference(reference).build(),
            Type.matcherRef
        )
    );
  }

  @Query("select t from Type t where t.operator.reference = :operator and t.active = :active")
  List<Type> findByOperatorAndActive(UUID operator, boolean active);
}
