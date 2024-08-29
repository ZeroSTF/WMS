package tn.zeros.template.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Transaction;
import tn.zeros.template.entities.User;

import tn.zeros.template.repositories.TransactionRepository;

import tn.zeros.template.services.IServices.ITransactionService;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findByUser(User user) {
        return transactionRepository.findTransactionByUser(user);
    }


    public List<Transaction> findAllById(Iterable<Long> ids) {
        List<Transaction> result = new ArrayList<>();
        for (Long id : ids) {
            transactionRepository.findById(id).ifPresent(result::add);
        }
        return result;
    }



}