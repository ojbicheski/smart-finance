package com.smartfinance.customer.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "customer", name = "tb_account")
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends AbstractReference {
  @Column(nullable = false)
  private String number;
  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name="account_type_id", nullable=false)
  private AccountType type;
}