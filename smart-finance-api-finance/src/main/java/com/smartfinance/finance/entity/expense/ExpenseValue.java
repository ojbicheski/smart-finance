package com.smartfinance.finance.entity.expense;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.entity.AbstractBasic;
import com.smartfinance.exchange.entity.Exchange;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "finance", name = "tb_expense_value")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExpenseValue extends AbstractBasic {
  @Column(nullable = false)
  private float amount;

  @ManyToOne
  @JoinColumn(name="expense_id", nullable=false, updatable = false)
  private Expense expense;

  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, updatable = false)
  private Currency currency;

  @ManyToOne
  @JoinColumn(name="exchange_id", updatable = false)
  private Exchange exchange;

  @Builder
  public ExpenseValue(float amount, Expense expense,
                      Currency currency, Exchange exchange) {
    this.amount = amount;
    this.expense = expense;
    this.currency = currency;
    this.exchange = exchange;
  }
}
