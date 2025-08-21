package com.smartfinance.operator.entity.product;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "financial_operator", name = "tb_product_detail")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductDetail extends AbstractReference {
  @ManyToOne
  @JoinColumn(name="product_id", nullable=false)
  private Product product;

  @Builder
  public ProductDetail(UUID reference) {
    super(reference);
  }
}
