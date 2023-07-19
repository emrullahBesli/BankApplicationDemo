package com.besliBank.bankapplication.dto.converter;

import com.besliBank.bankapplication.dto.TransactionDto;
import com.besliBank.bankapplication.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TransactionDtoConverter {

    public static TransactionDto transactionsDtoConvert(Transaction transaction) {

        return new TransactionDto(Objects.requireNonNull(transaction.getAmount())
                , Objects.requireNonNull(transaction.getTransactionDate()));
    }
}
