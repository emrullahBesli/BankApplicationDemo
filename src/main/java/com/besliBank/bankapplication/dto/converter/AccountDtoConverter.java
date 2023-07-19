package com.besliBank.bankapplication.dto.converter;

import com.besliBank.bankapplication.dto.AccountDto;
import com.besliBank.bankapplication.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class AccountDtoConverter {
    public static AccountDto accountsDtoConverter(Account account){
        return new AccountDto(account.getAccountName()
               ,account.getBalance()
                ,AccountCustomerDtoConverter.accountCustomerDtoConverter(Objects.requireNonNull(account.getCustomer()))
                , Objects.requireNonNull(account.getTransaction()).stream().map(TransactionDtoConverter::transactionsDtoConvert).collect(Collectors.toSet()));
    }

}
