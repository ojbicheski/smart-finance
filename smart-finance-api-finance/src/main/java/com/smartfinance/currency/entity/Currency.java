package com.smartfinance.currency.entity;

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
@Table(schema = "currency", name = "tb_currency")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Currency extends AbstractReference {
  @Column(nullable = false)
  private String code;

  @Builder
  public Currency(UUID reference, String code) {
    super(reference);
    this.code = code;
  }
}
