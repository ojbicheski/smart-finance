package com.smartfinance.product.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.Operator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_operator", name = "tb_product_type")
@Data
@EqualsAndHashCode(callSuper = true)
public class Type extends AbstractReference {
  @Column(nullable = false, unique = true)
  private String code;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false)
  private Operator operator;
}
