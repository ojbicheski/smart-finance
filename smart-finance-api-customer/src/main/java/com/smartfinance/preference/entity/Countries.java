package com.smartfinance.preference.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.file.Template;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "financial_operator", name = "tb_product")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Countries extends AbstractReference {
  @ManyToOne
  @JoinColumn(name="template_file_id", nullable=false)
  private Template template;
}
