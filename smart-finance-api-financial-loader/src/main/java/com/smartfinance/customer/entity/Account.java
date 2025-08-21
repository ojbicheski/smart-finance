package com.smartfinance.customer.entity;

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

  @Builder
  public Account(UUID reference) {
    super(reference);
  }
}