package com.smartfinance.transaction.entity;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.customer.entity.Account;
import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(schema = "transact", name = "tb_account_balance")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class Balance extends AbstractReference implements TargetDate {
  @Column(name = "balance_month", nullable = false, updatable = false)
  private int month;
  @Column(name = "balance_year", nullable = false, updatable = false)
  private int year;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BalanceStatus status;
  @Column(nullable = false)
  private float amount;

  @ManyToOne
  @JoinColumn(name="account_id", nullable=false, updatable = false)
  private Account account;

  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, updatable = false)
  private Currency currency;

  @Builder
  public Balance(UUID reference, int month, int year, float amount,
                 Account account, Currency currency) {
    super(reference);
    this.month = month;
    this.year = year;
    this.amount = amount;
    this.account = account;
    this.currency = currency;
    this.status = BalanceStatus.OPENED;
  }

  public Balance add(float value) {
    this.amount = this.amount + value;
    return this;
  }

  public Balance close() {
    this.status = BalanceStatus.CLOSED;
    return this;
  }
}
