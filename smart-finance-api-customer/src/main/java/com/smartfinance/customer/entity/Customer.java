package com.smartfinance.customer.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "customer", name = "tb_customer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends AbstractReference {
  @Column(nullable = false)
  private String name;
}
