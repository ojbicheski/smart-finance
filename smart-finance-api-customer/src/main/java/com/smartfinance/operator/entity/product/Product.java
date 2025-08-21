package com.smartfinance.operator.entity.product;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.entity.Activation;
import com.smartfinance.template.entity.Template;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "financial_operator", name = "tb_product")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Product extends AbstractReference implements Activation {
  @Column(nullable = false, unique = true)
  private String code;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private boolean active;

  @ManyToOne
  @JoinColumn(name="type_id", nullable=false)
  private Type type;
  @ManyToOne
  @JoinColumn(name="template_file_id", nullable=false)
  private Template template;

  @Builder
  public Product(UUID reference) {
    super(reference);
  }
}
