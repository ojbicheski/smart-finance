package com.smartfinance.operator.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "financial_operator", name = "tb_operator")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Operator extends AbstractReference {
  @Builder
  public Operator(UUID reference) {
    super(reference);
  }
}
