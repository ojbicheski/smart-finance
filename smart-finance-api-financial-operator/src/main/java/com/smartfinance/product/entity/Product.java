package com.smartfinance.product.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_operator", name = "tb_product")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractReference {
  @Column(nullable = false, unique = true)
  private String code;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="type_id", nullable=false)
  private Type type;
}
