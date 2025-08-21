package com.smartfinance.operator.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "shared_data", name = "tb_country")
@Data
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractReference {
  @Column(nullable = false)
  private String name;
  @Column(nullable = false, unique = true)
  private String code;
  @Column(nullable = false)
  private String display;
}
