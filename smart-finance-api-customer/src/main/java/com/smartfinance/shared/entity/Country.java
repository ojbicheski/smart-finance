package com.smartfinance.shared.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "shared_data", name = "tb_country")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Country extends AbstractReference {
  @Column(nullable = false, insertable = false, updatable = false)
  private String name;
  @Column(nullable = false, insertable = false, updatable = false)
  private String code;
  @Column(nullable = false, insertable = false, updatable = false)
  private String display;

  @Builder
  public Country(UUID reference) {
    super(reference);
  }
}
