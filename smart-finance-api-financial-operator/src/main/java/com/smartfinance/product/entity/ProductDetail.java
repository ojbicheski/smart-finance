package com.smartfinance.product.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_operator", name = "tb_product_detail")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDetail extends AbstractReference {
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="product_id", nullable=false)
  private Product product;
}
