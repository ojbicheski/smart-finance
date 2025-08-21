package com.smartfinance.exchange.entities;

import com.smartfinance.currency.entity.Currency;
import com.smartfinance.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(schema = "currency", name = "tb_exchange")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exchange extends AbstractEntity {
  @Column(nullable = false)
  private LocalDate exchanged;
  @Column(nullable = false)
  private double value;

  @ManyToOne
  @JoinColumn(name="currency_from", nullable=false)
  private Currency from;
  @ManyToOne
  @JoinColumn(name="currency_to", nullable=false)
  private Currency to;
}
