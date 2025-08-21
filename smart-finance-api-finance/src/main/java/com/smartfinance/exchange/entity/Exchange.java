package com.smartfinance.exchange.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(schema = "currency", name = "tb_exchange")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
public class Exchange extends AbstractReference {
  @Builder
  public Exchange(UUID reference) {
    super(reference);
  }
}
