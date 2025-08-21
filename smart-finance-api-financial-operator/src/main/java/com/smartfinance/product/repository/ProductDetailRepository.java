package com.smartfinance.product.repository;

import com.smartfinance.product.entity.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDetailRepository extends JpaRepository<Product, Long> {
  default Optional<Product> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Product.builder().reference(reference).build(),
            Product.matcherRef
        )
    );
  }

  @Query("select p from Product p where p.type.operator.reference = :operator and p.active = :active")
  List<Product> findByOperatorAndActive(UUID operator, boolean active);
}
