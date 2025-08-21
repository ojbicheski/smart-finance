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
public class Transact extends AbstractReference implements TargetDate {
  @Column(name = "transact_date", nullable = false, updatable = false)
  private LocalDate date;
  @Column(nullable = false, updatable = false)
  private String name;
  @Column(nullable = false, updatable = false)
  private String description;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactType type;
  @Column(name = "transact_month", nullable = false, updatable = false)
  private int month;
  @Column(name = "transact_year", nullable = false, updatable = false)
  private int year;
  @Column(nullable = false)
  private float amount;
  @Column(nullable = false)
  private boolean balanced;

  @ManyToOne
  @JoinColumn(name="account_id", nullable=false, updatable = false)
  private Account account;

  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, updatable = false)
  private Currency currency;

  @Builder
  public Transact(UUID reference, Account account,
                  Currency currency,
                  TransactEvent.Event event) {
    super(reference);
    this.account = account;
    this.currency = currency;
    if (event != null) {
      this.date = event.getDate();
      this.name = event.getName();
      this.description = event.getDescription();
      this.type = TransactType.valueOf(event.getType());
      this.month = date.getMonthValue();
      this.year = date.getYear();
      this.amount = event.getAmount();
    }
  }

  public float signedAmount() {
    if (amount <= 0) return amount;

    return TransactType.CREDIT.equals(type)
        ? amount : amount * -1;
  }
}
