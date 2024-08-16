package tn.zeros.template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.bon.sender = :user")
    List<Transaction> findTransactionByUser(User user);
}
