package com.smartfinance.customer.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.operator.entity.file.Template;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "customer", name = "tb_account_template")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AccountTemplate extends AbstractReference {
  @Column(nullable = false)
  private boolean active = true;

  @ManyToOne
  @JoinColumn(name="account_id", nullable=false, updatable = false)
  private Account account;
  @ManyToOne
  @JoinColumn(name="template_id", nullable=false, updatable = false)
  private Template template;

  @Builder
  public AccountTemplate(Account account, Template template) {
    this.account = account;
    this.template = template;
  }
}