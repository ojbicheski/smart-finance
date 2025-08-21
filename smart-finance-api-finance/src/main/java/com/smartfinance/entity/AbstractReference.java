package com.smartfinance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
@ToString
public class AbstractReference {
  public static ExampleMatcher matcherRef = ExampleMatcher.matching()
      .withIgnorePaths("id", "version", "active")
      .withMatcher("reference", GenericPropertyMatcher::exact)
      .withIgnoreNullValues();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(unique = true, updatable = false, insertable = false)
  private UUID reference;
  @Version
  private int version;

  @Column(nullable = false, updatable = false)
  private ZonedDateTime created;
  @Column(nullable = false)
  private ZonedDateTime updated;

  public AbstractReference(UUID reference) {
    this.reference = reference;
  }

  @PrePersist
  public void onPersist() {
    created = ZonedDateTime.now(ZoneId.of("UTC"));
    onUpdate();
  }

  @PreUpdate
  public void onUpdate() {
    updated = ZonedDateTime.now(ZoneId.of("UTC"));
  }

  public boolean isUpdate() {
    return reference != null;
  }
}
