package com.smartfinance.product.repository;

import com.smartfinance.product.entity.ProductDetail;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

  @Query("select p from ProductDetail p where p.product.reference = :product and p.active = :active")
  List<ProductDetail> findByProductAndActive(UUID product, boolean active);
}
