package com.smartfinance.template.repository;

import com.smartfinance.template.entity.Format;
import com.smartfinance.template.entity.Template;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
  default Optional<Template> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Template.builder().reference(reference).build(),
            Template.matcherRef
        )
    );
  }

  @Query("select t from Template t where t.operator.reference = :operator and t.active = :active")
  List<Template> findByOperatorAndActive(UUID operator, boolean active);
}
