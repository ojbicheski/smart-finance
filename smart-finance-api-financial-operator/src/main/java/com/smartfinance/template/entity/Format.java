package com.smartfinance.template.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.entity.Activation;
import com.smartfinance.operator.entity.Operator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "financial_operator", name = "tb_format_file")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Format extends AbstractReference implements Activation {
  @Column(nullable = false)
  private String extension;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false, updatable = false)
  private Operator operator;

  @Builder
  public Format(UUID reference) {
    super(reference);
  }
}
