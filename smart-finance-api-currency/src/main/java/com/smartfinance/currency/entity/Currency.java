package com.smartfinance.currency.entity;

import com.smartfinance.entity.AbstractEntity;
import com.smartfinance.shared.entity.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "currency", name = "tb_currency")
@Data
@EqualsAndHashCode(callSuper = true)
public class Currency extends AbstractEntity {
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String code;
  @Column(nullable = false)
  private String symbol;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="country_id", nullable=false)
  private Country country;
}
