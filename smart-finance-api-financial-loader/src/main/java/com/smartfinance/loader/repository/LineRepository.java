package com.smartfinance.loader.repository;

import com.smartfinance.loader.entity.FileLineStatus;
import com.smartfinance.loader.entity.Line;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
  default Optional<Line> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Line.builder().reference(reference).build(),
            Line.matcherRef
        )
    );
  }

  @Query("SELECT DISTINCT l.status " +
      "from Line l " +
      "where l.file.reference = :file")
  List<FileLineStatus> allCompleted(UUID file);
}
