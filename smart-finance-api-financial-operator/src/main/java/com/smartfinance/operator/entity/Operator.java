package com.smartfinance.operator.entity;

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
@Table(schema = "financial_operator", name = "tb_operator")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Operator extends AbstractReference implements Activation {
  @Column(nullable = false, unique = true)
  private String swiftCode;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String display;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="country_id", nullable=false)
  private Country country;

  @Builder
  public Operator(UUID reference) {
    super(reference);
  }
}
