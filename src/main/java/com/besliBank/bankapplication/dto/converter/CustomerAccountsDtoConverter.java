package com.besliBank.bankapplication.dto.converter;

import com.besliBank.bankapplication.dto.CustomerAccountsDto;
import com.besliBank.bankapplication.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class CustomerAccountsDtoConverter {
    public static CustomerAccountsDto customerAccountsDtoConverter(Account account){
        return new CustomerAccountsDto(account.getAccountName()
               ,account.getBalance(),
                Objects.requireNonNull(account.getTransaction()).stream().map(TransactionDtoConverter::transactionsDtoConvert).collect(Collectors.toSet()));
    }
}
