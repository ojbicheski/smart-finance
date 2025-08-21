package com.smartfinance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
public class AbstractBasic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, updatable = false)
  private ZonedDateTime created;
  @Column(nullable = false)
  private ZonedDateTime updated;

  @PrePersist
  public void onPersist() {
    created = ZonedDateTime.now(ZoneId.of("UTC"));
    onUpdate();
  }

  @PreUpdate
  public void onUpdate() {
    updated = ZonedDateTime.now(ZoneId.of("UTC"));
  }
}
