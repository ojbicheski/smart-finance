package com.smartfinance.operator.repository;

import com.smartfinance.operator.entity.product.ProductDetail;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
  default Optional<ProductDetail> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            ProductDetail.builder().reference(reference).build(),
            ProductDetail.matcherRef
        )
    );
  }
}
