package com.smartfinance.customer.repository;

import com.smartfinance.transaction.entity.Transact;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Transact, Long> {
  default Optional<Transact> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Transact.builder().reference(reference).build(),
            Transact.matcherRef
        )
    );
  }

//  default List<Transact> search(Transact transact) {
//    return this.findAll(Example.of(transact, transact.matcher()));
//  }

//  @Query("select g from Group g where g.customer.reference = :customer and g.active = :active")
//  List<Group> findByCustomerReferenceAndActive(UUID customer, boolean active);
}
