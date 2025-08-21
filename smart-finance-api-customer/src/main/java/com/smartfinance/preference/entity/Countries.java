package com.smartfinance.preference.entity;

import com.smartfinance.customer.entity.Customer;
import com.smartfinance.entity.AbstractReference;
import com.smartfinance.shared.entity.Country;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "customer", name = "tb_preference_countries")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Countries extends AbstractReference {
  @ManyToOne
  @JoinColumn(name="country_id", nullable=false)
  private Country country;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false)
  private Customer customer;

  @Builder
  public Countries(UUID reference, Country country, Customer customer) {
    super(reference);
    this.country = country;
    this.customer = customer;
  }
}
