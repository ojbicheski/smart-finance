package com.smartfinance.product.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.entity.Activation;
import com.smartfinance.operator.entity.Operator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "financial_operator", name = "tb_product_type")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Type extends AbstractReference implements Activation {
  @Column(nullable = false, unique = true)
  private String code;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false)
  private Operator operator;

  @Builder
  public Type(UUID reference) {
    super(reference);
  }
}
