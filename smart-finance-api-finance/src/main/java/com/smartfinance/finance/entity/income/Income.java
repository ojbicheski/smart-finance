package com.smartfinance.finance.entity.income;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.entity.AbstractReference;
import com.smartfinance.finance.entity.expense.Status;
import com.smartfinance.finance.entity.expense.Type;
import com.smartfinance.finance.entity.expense.Value;
import com.smartfinance.transaction.entity.Transact;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "finance", name = "tb_expense")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Income extends AbstractReference {
  @Column(name = "expense_date", nullable = false, updatable = false)
  private LocalDate date;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;
  @Column(name = "expense_month", nullable = false, updatable = false)
  private int month;
  @Column(name = "expense_year", nullable = false, updatable = false)
  private int year;

  @ManyToOne
  @JoinColumn(name="transact_id", nullable=false, updatable = false)
  private Transact transact;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false, updatable = false)
  private Customer customer;

  @ManyToOne
  @JoinColumn(name="expense_type_id", nullable=false, updatable = false)
  private com.smartfinance.finance.entity.expense.Type type;

  @OneToMany(mappedBy = "expense")
  private List<Value> values;

  @Builder
  public Income(UUID reference, LocalDate date, Status status,
                Transact transact, Customer customer, Type type) {
    super(reference);
    this.date = date;
    if (status != null) {
      this.status = status;
    } else {
      status = Status.PENDING;
    }
    if (date != null) {
      this.month = date.getMonthValue();
      this.year = date.getYear();
    }
    this.transact = transact;
    this.customer = customer;
    this.type = type;
  }
}
