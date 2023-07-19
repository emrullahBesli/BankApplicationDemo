package com.besliBank.bankapplication.repository;

import com.besliBank.bankapplication.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String> {
    Transaction getByAccountId(String Id);
}
