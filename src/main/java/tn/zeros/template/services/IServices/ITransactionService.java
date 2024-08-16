package tn.zeros.template.services.IServices;

import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;

import java.util.List;

public interface ITransactionService {
    List<Transaction> findAll();
    Transaction findById(Long id);
    Transaction save(Transaction transaction);
    void deleteById(Long id);
    List<Transaction> findByUser(User user);
    //void sendTransactions(List<Long> transactionIds, Long receiverId);

}
