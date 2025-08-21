package com.smartfinance.operator.entity.file;

import com.smartfinance.entity.AbstractBasic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_operator", name = "tb_format_file")
@Data
@EqualsAndHashCode(callSuper = true)
public class Format extends AbstractBasic {
  @Column(nullable = false)
  private String extension;
}
