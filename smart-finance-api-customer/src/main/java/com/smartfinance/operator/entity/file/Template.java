package com.smartfinance.operator.entity.file;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.operator.entity.Operator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(schema = "financial_operator", name = "tb_template_file")
@Data
@EqualsAndHashCode(callSuper = true)
public class Template extends AbstractReference {
  @Column(nullable = false, updatable = false)
  private String name;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false, updatable = false)
  private String templateVersion;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private Layout layout;

  @ManyToOne
  @JoinColumn(name="format_id", nullable=false, updatable = false)
  private Format format;
  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false, updatable = false)
  private Operator operator;

  @Setter
  @Getter
  public static class Layout {
    private int[] ignoreLines;
    private List<Position> positions;
  }

  @Setter
  @Getter
  public static class Position {
    private int sequence;
    private PositionType type;
    private String mapTo;
    private String[] removeCharacters;
  }

  public static enum PositionType {
    STRING, DECIMAL, DATE
  }
}
