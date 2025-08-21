package com.smartfinance.customer.entity;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "customer", name = "tb_account")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Account extends AbstractReference {
  @Column(name = "number_acct", nullable = false)
  private String number;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false, updatable = false)
  private Customer customer;

  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, updatable = false)
  private Currency currency;

  @Builder
  public Account(UUID reference) {
    super(reference);
  }
}