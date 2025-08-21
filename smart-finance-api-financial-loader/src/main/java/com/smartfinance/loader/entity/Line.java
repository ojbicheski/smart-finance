package com.smartfinance.loader.entity;

import com.smartfinance.entity.AbstractReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "financial_loader", name = "tb_file_line")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Line extends AbstractReference {
  @Column(nullable = false)
  private int lineNumber;
  @Column(nullable = false)
  private String line;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FileLineStatus status;
  @Column
  private String reasonFail;

  @ManyToOne
  @JoinColumn(name = "file_id", nullable = false)
  private File file;

  @Builder
  public Line(UUID reference, int lineNumber, String line, File file) {
    super(reference);
    this.lineNumber = lineNumber;
    this.line = line;
    this.file = file;
    this.status = FileLineStatus.IMPORTED;
  }

  public String value(int position) {
    if (position < 0) {
      throw new IllegalArgumentException("Invalid position [%s]".formatted(position));
    }
    String[] positions = line.split(",");
    if (position >= positions.length) {
      throw new IllegalArgumentException(
          "Invalid position [%s]. Exceed limit [%s]".formatted(position, positions.length - 1));
    }

    return positions[position];
  }
}
