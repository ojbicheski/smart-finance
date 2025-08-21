package com.smartfinance.finance.entity.income;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "finance", name = "tb_income_type")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class IncomeType extends AbstractReference {
  @Column(nullable = false, updatable = false)
  private String name;
  @Column(nullable = false, updatable = false)
  private String description;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false, updatable = false)
  private Customer customer;

  @Builder
  public IncomeType(UUID reference) {
    super(reference);
  }
}
