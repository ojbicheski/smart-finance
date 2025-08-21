package com.smartfinance.loader.entity;

import com.smartfinance.customer.entity.Account;
import com.smartfinance.entity.AbstractReference;
import com.smartfinance.loader.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "financial_loader", name = "tb_file")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Slf4j
public class File extends AbstractReference {
  public static ExampleMatcher matcherRef = AbstractReference.matcherRef
      .withIgnorePaths("status");

  @Column(nullable = false)
  private String fileName;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileStatus status;

  @OneToMany(mappedBy = "file", fetch = FetchType.LAZY)
  private List<Line> lines;

  @ManyToOne
  @JoinColumn(name="account_id", nullable=false)
  private Account account;

  @Transient
  private transient byte[] data;

  @Builder
  public File(MultipartFile file, Account account, UUID reference) {
    super(reference);
    this.account = account;
    this.status = FileStatus.UPLOADED;

    if (file != null) {
      this.fileName = file.getOriginalFilename();
      try {
        this.data = file.getBytes();
      } catch (IOException e) {
        this.status = FileStatus.FAIL_TO_LOAD;
        throw new BadRequestException("Fail to load File data", e);
      }
    }
  }

  public void addLine(Line line) {
    if (Objects.isNull(lines)) lines = new ArrayList<>();
    lines.add(line);
  }

  public void setStatus(String status) {
    this.status = FileStatus.of(status);
  }

  public void setStatus(FileStatus status) {
    this.status = status;
  }
}
