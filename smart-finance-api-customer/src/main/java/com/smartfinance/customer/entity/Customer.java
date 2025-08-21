package com.smartfinance.customer.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "customer", name = "tb_customer")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customer extends AbstractReference {
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private boolean active = true;

  @Builder
  public Customer(UUID reference) {
    super(reference);
  }
}
