package com.smartfinance.shared.entity;

import com.smartfinance.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "shared_data", name = "tb_country")
@Data
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractEntity {
  @Column(nullable = false, insertable = false, updatable = false)
  private String name;
  @Column(nullable = false, insertable = false, updatable = false)
  private String code;
  @Column(nullable = false, insertable = false, updatable = false)
  private String display;
}
