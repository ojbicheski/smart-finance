package com.smartfinance.customer.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.Operator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "customer", name = "tb_account")
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountTemplate extends AbstractReference {
  @Column(name = "number_acct", nullable = false)
  private String number;
  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false)
  private Customer customer;
  @ManyToOne
  @JoinColumn(name="account_type_id", nullable=false)
  private AccountType type;
  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false)
  private Operator operator;
}