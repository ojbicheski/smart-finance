package com.smartfinance.template.entity;

import com.smartfinance.entity.AbstractReference;
import com.smartfinance.entity.Activation;
import com.smartfinance.operator.entity.Operator;
import com.smartfinance.template.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "financial_operator", name = "tb_template_file")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Template extends AbstractReference implements Activation {
  @Column(nullable = false, updatable = false)
  private String name;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false, updatable = false)
  private String templateVersion;
  @Column(nullable = false)
  private boolean active;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private Layout layout;

  @ManyToOne
  @JoinColumn(name="format_id", nullable=false, updatable = false)
  private Format format;
  @ManyToOne
  @JoinColumn(name="operator_id", nullable=false, updatable = false)
  private Operator operator;

  @Builder
  public Template(UUID reference) {
    super(reference);
  }

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

    public void setType(String type) {
      if (type == null) return;
      this.type = PositionType.of(type);
    }
  }

  public static enum PositionType {
    STRING, DECIMAL, DATE;

    public static PositionType of(String type) {
      return Arrays.stream(values())
          .filter(t -> t.name().equalsIgnoreCase(type))
          .findFirst()
          .orElseThrow(() -> new BadRequestException("Type %s is invalid.".formatted(type)));
    }
  }
}
