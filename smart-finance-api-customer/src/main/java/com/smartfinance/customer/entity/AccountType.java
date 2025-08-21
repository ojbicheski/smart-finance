package com.smartfinance.customer.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.entity.Activation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "customer", name = "tb_account_type")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AccountType extends AbstractReference implements Activation {
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private boolean active;

  @Builder
  public AccountType(UUID reference) {
    super(reference);
  }
}
