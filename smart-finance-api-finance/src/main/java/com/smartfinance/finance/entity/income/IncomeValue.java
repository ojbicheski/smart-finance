package com.smartfinance.finance.entity.income;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.entity.AbstractBasic;
import com.smartfinance.exchange.entity.Exchange;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "finance", name = "tb_income_value")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class IncomeValue extends AbstractBasic {
  @Column(nullable = false)
  private float amount;

  @ManyToOne
  @JoinColumn(name="income_id", nullable=false, updatable = false)
  private Income income;

  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, updatable = false)
  private Currency currency;

  @ManyToOne
  @JoinColumn(name="exchange_id", updatable = false)
  private Exchange exchange;

  @Builder
  public IncomeValue(float amount, Income income,
                     Currency currency, Exchange exchange) {
    this.amount = amount;
    this.income = income;
    this.currency = currency;
    this.exchange = exchange;
  }
}
