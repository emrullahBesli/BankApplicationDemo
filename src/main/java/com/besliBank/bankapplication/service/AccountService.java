package com.besliBank.bankapplication.service;

import com.besliBank.bankapplication.dto.AccountDto;
import com.besliBank.bankapplication.dto.CreateAccountRequest;
import com.besliBank.bankapplication.dto.DepositOrWithDrawRequest;
import com.besliBank.bankapplication.dto.converter.AccountCustomerDtoConverter;
import com.besliBank.bankapplication.dto.converter.AccountDtoConverter;
import com.besliBank.bankapplication.dto.converter.TransactionDtoConverter;
import com.besliBank.bankapplication.exception.AccountNotFoundException;
import com.besliBank.bankapplication.exception.InsufficientBalanceException;
import com.besliBank.bankapplication.model.Account;
import com.besliBank.bankapplication.model.Customer;
import com.besliBank.bankapplication.repository.AccountRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public AccountService(AccountRepository accountRepository, CustomerService customerService, TransactionService transactionService, KafkaTemplate<String, String> kafkaTemplate) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.kafkaTemplate = kafkaTemplate;
    }


    public AccountDto createNewAccount(CreateAccountRequest request) {
        Customer customer = customerService.getCustomerByTc(request.getTc());
        Account account = new Account(request.getAccountName(),customer);
        accountRepository.save(account);
        String message = "Account has been created : " + account.getAccountName();
        kafkaTemplate.send("information", message);
        return new AccountDto(account.getAccountName(), account.getBalance()
                , AccountCustomerDtoConverter.accountCustomerDtoConverter(Objects.requireNonNull(account.getCustomer()))
                , Objects.requireNonNull(account.getTransaction()).stream().map(TransactionDtoConverter::transactionsDtoConvert).collect(Collectors.toSet()));
    }

    public List<AccountDto> getAllAccounts() {
        List<Account> account = accountRepository.findAll();
        return account.stream().map(AccountDtoConverter::accountsDtoConverter).collect(Collectors.toList());
    }

    public AccountDto withdrawMoney(DepositOrWithDrawRequest request) {
        Account account = accountRepository.findById(request.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account could not found by id:" + request.getId()));

        if (request.getAmount().compareTo(account.getBalance()) <= 0 && request.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            account.setBalance(account.getBalance().subtract(request.getAmount()));
           transactionService.createTransaction(request.getAmount(), LocalDateTime.now(), account);
            accountRepository.save(account);
            return new AccountDto(account.getAccountName(), account.getBalance()
                    , AccountCustomerDtoConverter.accountCustomerDtoConverter(Objects.requireNonNull(account.getCustomer()))
                   , Objects.requireNonNull(account.getTransaction()).stream().map(TransactionDtoConverter::transactionsDtoConvert).collect(Collectors.toSet()));

        } else throw new InsufficientBalanceException("Insufficient balance or meaningless request");

    }

    public AccountDto depositMoney(DepositOrWithDrawRequest request) {
        Account account =  accountRepository.findById(request.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account could not found by id:" + request.getId()));
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);
        transactionService.createTransaction(request.getAmount(),LocalDateTime.now(),account);
        return new AccountDto(account.getAccountName(), account.getBalance()
                , AccountCustomerDtoConverter.accountCustomerDtoConverter(Objects.requireNonNull(account.getCustomer()))
                , Objects.requireNonNull(account.getTransaction()).stream().map(TransactionDtoConverter::transactionsDtoConvert).collect(Collectors.toSet()));

    }


}
