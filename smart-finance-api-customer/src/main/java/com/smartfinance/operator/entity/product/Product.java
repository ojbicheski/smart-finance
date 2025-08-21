package com.smartfinance.operator.entity.product;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.file.Template;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "financial_operator", name = "tb_product")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Product extends AbstractReference {
  @ManyToOne
  @JoinColumn(name="template_file_id", nullable=false)
  private Template template;
}
