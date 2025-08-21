package com.smartfinance.customer.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@Table(schema = "customer", name = "tb_account")
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends AbstractReference {
  @Column(nullable = false)
  private String number;
  @Column(nullable = false)
  private boolean active;

  @Builder
  public Account(UUID reference) {
    super(reference);
  }
}