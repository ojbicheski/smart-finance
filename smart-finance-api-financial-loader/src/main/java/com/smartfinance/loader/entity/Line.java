package com.smartfinance.loader.entity;

import com.smartfinance.entity.AbstractBasic;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "financial_loader", name = "tb_file_line")
@Data
@EqualsAndHashCode(callSuper = true)
public class Line extends AbstractBasic {
  @Column(nullable = false)
  private int lineNumber;
  @Column(nullable = false)
  private String line;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FileLineStatus status;

  @ManyToOne
  @JoinColumn(name = "file_id", nullable = false)
  private File file;
}
