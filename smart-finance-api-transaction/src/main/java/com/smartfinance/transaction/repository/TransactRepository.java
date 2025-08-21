package com.smartfinance.transaction.repository;

import com.smartfinance.customer.entity.Account;
import com.smartfinance.transaction.entity.Transact;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactRepository extends JpaRepository<Transact, Long> {
  default Optional<Transact> findByReference(UUID reference) {
    return this.findOne(
        Example.of(
            Transact.builder().reference(reference).build(),
            Transact.matcherRef
        )
    );
  }

  @Query("SELECT t FROM Transact t " +
      "WHERE t.date = :#{#transact.date} " +
      "AND t.name = :#{#transact.name} " +
      "AND t.description = :#{#transact.description} " +
      "AND t.type = :#{#transact.type} " +
      "AND t.year = :#{#transact.year} " +
      "AND t.month = :#{#transact.month} " +
      "AND t.account.id = :#{#transact.account.id}")
  List<Transact> findDuplicated(Transact transact);

  @Query("SELECT t from Transact t " +
      "WHERE t.account.id = :#{#account.id} " +
      "AND balanced = false")
  List<Transact> transactionsToUpdateBalance(Account account);

//  default List<Transact> search(Transact transact) {
//    return this.findAll(Example.of(transact, transact.matcher()));
//  }

//  @Query("select g from Group g where g.customer.reference = :customer and g.active = :active")
//  List<Group> findByCustomerReferenceAndActive(UUID customer, boolean active);
}
