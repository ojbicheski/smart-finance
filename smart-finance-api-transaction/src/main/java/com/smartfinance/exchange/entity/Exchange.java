package com.smartfinance.exchange.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(schema = "currency", name = "tb_exchange")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Exchange extends AbstractReference {
}
