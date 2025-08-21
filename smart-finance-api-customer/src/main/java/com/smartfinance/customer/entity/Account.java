package com.smartfinance.customer.entity;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.entity.AbstractReference;
import com.smartfinance.entity.Activation;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.operator.entity.file.Template;
import com.smartfinance.operator.entity.product.ProductDetail;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.ExampleMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "customer", name = "tb_account")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Account extends AbstractReference implements Activation {
  @Column(name = "number_acct", nullable = false)
  private String number;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false, updatable = false)
  private Customer customer;
  @ManyToOne
  @JoinColumn(name="account_type_id", nullable=false)
  private AccountType type;
  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false, updatable = false)
  private Operator operator;
  @ManyToOne
  @JoinColumn(name="product_detail_id", nullable=false, updatable = false)
  private ProductDetail detail;
  @ManyToOne
  @JoinColumn(name="currency_id", nullable=false, updatable = false)
  private Currency currency;

  @OneToMany(mappedBy = "account")
  private List<AccountTemplate> templates = new ArrayList<>();

  @Builder
  public Account(UUID reference, Customer customer, AccountType type,
                 Operator operator, ProductDetail detail,
                 Currency currency) {
    super(reference);
    this.customer  = customer;
    this.type = type;
    this.operator = operator;
    this.detail = detail;
    this.currency = currency;
  }

  public Template getTemplate() {
    return getTemplates().stream()
        .filter(at -> at.isActive())
        .findFirst()
        .map(AccountTemplate::getTemplate)
        .orElse(null);
  }

  public ExampleMatcher matcher() {
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withIgnorePaths("id", "version", "reference")
        .withIgnoreNullValues();

    if (customer != null) {
      matcher.withMatcher("customer", ExampleMatcher.GenericPropertyMatcher::exact);
    }
    if (number != null) {
      matcher.withMatcher("number", ExampleMatcher.GenericPropertyMatcher::startsWith);
    }


    return matcher;
  }
}