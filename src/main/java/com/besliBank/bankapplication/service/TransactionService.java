package com.besliBank.bankapplication.service;

import com.besliBank.bankapplication.model.Account;
import com.besliBank.bankapplication.model.Transaction;
import com.besliBank.bankapplication.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(BigDecimal amount,LocalDateTime localDateTime,Account account) {
        transactionRepository.save(new Transaction(amount,localDateTime,account));
    }

    public Transaction findTransactionByAccountId(String id){
       return transactionRepository.getByAccountId(id);
    }





}
