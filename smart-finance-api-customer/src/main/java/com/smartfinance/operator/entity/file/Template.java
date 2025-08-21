package com.smartfinance.operator.entity.file;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.Operator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_operator", name = "tb_template_file")
@Data
@EqualsAndHashCode(callSuper = true)
public class Template extends AbstractReference {
  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false, updatable = false)
  private Operator operator;
}
