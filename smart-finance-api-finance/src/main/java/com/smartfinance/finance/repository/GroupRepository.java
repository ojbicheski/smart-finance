package com.smartfinance.finance.repository;

import com.smartfinance.finance.entity.expense.Group;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
  default Optional<Group> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Group.builder().reference(reference).build(),
            Group.matcherRef
        )
    );
  }

  default List<Group> search(Group group) {
    return this.findAll(Example.of(group, group.matcher()));
  }

  @Query("select g from Group g where g.customer.reference = :customer and g.active = :active")
  List<Group> findByCustomerAndActive(UUID customer, boolean active);
}
