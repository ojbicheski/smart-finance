package com.smartfinance.transaction.entity;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "transact", name = "tb_transact")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transact extends AbstractReference {
  @Column(name = "transact_date", nullable = false, insertable = false, updatable = false)
  private LocalDate date;
  @Column(nullable = false, insertable = false, updatable = false)
  private String name;
  @Column(nullable = false, insertable = false, updatable = false)
  private String description;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, insertable = false, updatable = false)
  private TransactType type;
  @Column(name = "transact_month", nullable = false, insertable = false, updatable = false)
  private int month;
  @Column(name = "transact_year", nullable = false, insertable = false, updatable = false)
  private int year;
  @Column(nullable = false, insertable = false, updatable = false)
  private float amount;

  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, insertable = false, updatable = false)
  private Currency currency;

  @Builder
  public Transact(UUID reference) {
    super(reference);
  }
}
