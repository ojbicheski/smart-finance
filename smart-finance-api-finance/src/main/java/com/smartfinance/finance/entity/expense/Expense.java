package com.smartfinance.finance.entity.expense;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.entity.AbstractReference;
import com.smartfinance.transaction.entity.Transact;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "finance", name = "tb_expense")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class Expense extends AbstractReference {
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
  @JoinColumn(name="expense_type_id", updatable = false)
  private ExpenseType type;

  @OneToMany(mappedBy = "expense",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  private List<ExpenseValue> values;

  @Builder
  public Expense(UUID reference, Status status,
                 Transact transact, Customer customer,
                 List<ExpenseValue> values) {
    super(reference);
    this.date = transact.getDate();
    this.status = Objects.requireNonNullElse(status, Status.PENDING);
    if (date != null) {
      this.month = date.getMonthValue();
      this.year = date.getYear();
    }
    this.transact = transact;
    this.customer = customer;
    values.forEach(v -> v.setExpense(this));
    this.values = values;
  }
}
