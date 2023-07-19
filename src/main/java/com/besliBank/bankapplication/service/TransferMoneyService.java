package com.besliBank.bankapplication.service;

import com.besliBank.bankapplication.dto.TransferMoneyRequest;
import com.besliBank.bankapplication.exception.AccountNotFoundException;
import com.besliBank.bankapplication.exception.InsufficientBalanceException;
import com.besliBank.bankapplication.model.Account;
import com.besliBank.bankapplication.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Service
public class TransferMoneyService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    private final RabbitTemplate rabbitTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DirectExchange exchange;
    private final Logger logger = LoggerFactory.getLogger(TransferMoneyService.class);

    @Value("${sample.rabbitmq.routingKey}")
    private String routingKey;


    public TransferMoneyService(AccountRepository accountRepository, TransactionService transactionService, RabbitTemplate rabbitTemplate, KafkaTemplate<String, String> kafkaTemplate, DirectExchange exchange) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.exchange = exchange;
    }

    public String startTransferProcess(TransferMoneyRequest request) {
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, request);
        return "Your transaction has been successfully received";
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void transferMoney(TransferMoneyRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account cannot be found by id"));
        Account targetAccount = accountRepository.findById(request.getTargetAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Target Account cannot be found by id"));

        if (request.getAmount().compareTo(account.getBalance()) <= 0 && request.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            account.setBalance(account.getBalance().subtract(request.getAmount()));
            targetAccount.setBalance(targetAccount.getBalance().add(request.getAmount()));
            accountRepository.saveAll(Arrays.asList(account, targetAccount));
            rabbitTemplate.convertAndSend(exchange.getName(), "secondRoute", request);
        } else {
            throw new InsufficientBalanceException("Insufficient balance or meaningless request");
        }
    }

    @RabbitListener(queues = "secondStepQueue")
    public void completingTransfer(TransferMoneyRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account cannot be found by id"));
        Account targetAccount = accountRepository.findById(request.getTargetAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Target Account cannot be found by id"));


        transactionService.createTransaction(request.getAmount(), LocalDateTime.now(), account);
        transactionService.createTransaction(request.getAmount(), LocalDateTime.now(), targetAccount);

        String messageSender = "Dear " + Objects.requireNonNull(account.getCustomer()).getName() +
                " your money has been sent. Your remaining balance: " + account.getBalance();
        String messageReceiver = "Dear " + Objects.requireNonNull(targetAccount.getCustomer()).getName() +
                " money has been added to your account. Your new balance: " + targetAccount.getBalance();

        String emailMessageSender = account.getCustomer().getEmail() + "," + "Money Transfer" + "," + messageSender;
        String emailMessageReceiver = targetAccount.getCustomer().getEmail() + "," + "Money Transfer" + "," + messageReceiver;
        logger.info(" Transaction has been successfully done");

        kafkaTemplate.send("transfer-notification", emailMessageSender);
        kafkaTemplate.send("transfer-notification", emailMessageReceiver);


    }
}
