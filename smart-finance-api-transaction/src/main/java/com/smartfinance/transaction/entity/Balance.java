package com.smartfinance.transaction.entity;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.customer.entity.Account;
import com.smartfinance.entity.AbstractReference;
import com.smartfinance.transaction.mq.model.TransactEvent;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Balance extends AbstractReference {
  @Column(nullable = false, updatable = false)
  private LocalDate date;
  @Column(nullable = false, updatable = false)
  private String name;
  @Column(nullable = false, updatable = false)
  private String description;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactType type;
  @Column(nullable = false)
  private float value;

  @ManyToOne
  @JoinColumn(name="account_id", nullable=false, updatable = false)
  private Account account;

  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, updatable = false)
  private Currency currency;

  @Builder
  public Balance(UUID reference, Account account,
                 Currency currency,
                 TransactEvent.Event event) {
    super(reference);
    this.account = account;
    this.currency = currency;
    this.date = event.getDate();
    this.name = event.getName();
    this.description = event.getDescription();
    this.type = TransactType.valueOf(event.getType());
    this.value = event.getValue();
  }
}
