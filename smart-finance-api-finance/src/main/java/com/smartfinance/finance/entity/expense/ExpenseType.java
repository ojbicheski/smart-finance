package com.smartfinance.finance.entity.expense;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.entity.Activation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.ExampleMatcher;

import java.util.UUID;

@Entity
@Table(schema = "finance", name = "tb_expense_type")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExpenseType extends AbstractReference implements Activation {
  @Column(nullable = false, updatable = false)
  private String name;
  @Column(nullable = false, updatable = false)
  private String description;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="expense_group_id", nullable=false, updatable = false)
  private Group group;

  @Builder
  public ExpenseType(UUID reference) {
    super(reference);
  }

  public ExampleMatcher matcher() {
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withIgnorePaths("id", "version", "reference")
        .withIgnoreNullValues();

    if (group != null) {
      matcher.withMatcher("group", ExampleMatcher.GenericPropertyMatcher::exact);
    }
    if (name != null) {
      matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatcher::startsWith);
    }

    return matcher;
  }
}
