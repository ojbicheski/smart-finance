package com.smartfinance.operator.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.shared.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_operator", name = "tb_operator")
@Data
@EqualsAndHashCode(callSuper = true)
public class Operator extends AbstractReference {
  @Column(nullable = false, unique = true)
  private String swiftCode;
  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name="country_id", nullable=false)
  private Country country;
}
