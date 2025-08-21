package com.smartfinance.template.dto;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_operator", name = "tb_format_file")
@Data
@EqualsAndHashCode(callSuper = true)
public class FormatDTO extends AbstractReference {
  @Column(nullable = false)
  private String extension;
  @Column(nullable = false)
  private boolean active;
}
