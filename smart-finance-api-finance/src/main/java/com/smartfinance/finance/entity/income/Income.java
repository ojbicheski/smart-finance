package com.smartfinance.finance.entity.income;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.entity.AbstractReference;
import com.smartfinance.transaction.entity.Transact;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "finance", name = "tb_income")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Income extends AbstractReference {
  @Column(name = "income_date", nullable = false, updatable = false)
  private LocalDate date;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;
  @Column(name = "income_month", nullable = false, updatable = false)
  private int month;
  @Column(name = "income_year", nullable = false, updatable = false)
  private int year;

  @ManyToOne
  @JoinColumn(name="transact_id", nullable=false, updatable = false)
  private Transact transact;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false, updatable = false)
  private Customer customer;

  @ManyToOne
  @JoinColumn(name="income_type_id", updatable = false)
  private IncomeType type;

  @OneToMany(mappedBy = "income",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  private List<IncomeValue> values;

  @Builder
  public Income(UUID reference, Status status,
                Transact transact, Customer customer,
                List<IncomeValue> values) {
    super(reference);
    this.date = transact.getDate();
    this.status = Objects.requireNonNullElse(status, Status.PENDING);
    if (date != null) {
      this.month = date.getMonthValue();
      this.year = date.getYear();
    }
    this.transact = transact;
    this.customer = customer;
    values.forEach(v -> v.setIncome(this));
    this.values = values;
  }
}
