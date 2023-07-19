package com.besliBank.bankapplication.controller;

import com.besliBank.bankapplication.dto.AccountDto;
import com.besliBank.bankapplication.dto.CreateAccountRequest;
import com.besliBank.bankapplication.dto.DepositOrWithDrawRequest;
import com.besliBank.bankapplication.dto.TransferMoneyRequest;
import com.besliBank.bankapplication.service.AccountService;
import com.besliBank.bankapplication.service.TransferMoneyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Validated
@RequestMapping("/v1/besliBank/account")
public class AccountController {
private final AccountService accountService;
private final TransferMoneyService transferMoneyService;

    public AccountController(AccountService accountService, TransferMoneyService transferMoneyService) {
        this.accountService = accountService;
        this.transferMoneyService = transferMoneyService;
    }

    @GetMapping("/allAccounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }


    @PostMapping("/createAccount")
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createNewAccount(request));
    }

    @PutMapping("/withDraw")
    public ResponseEntity<AccountDto> withDrawMoney(@RequestBody @Valid DepositOrWithDrawRequest request) {
        return ResponseEntity.ok(accountService.withdrawMoney(request));
    }

    @PutMapping("/deposit")
    public ResponseEntity<AccountDto>depositMoney(@RequestBody @Valid DepositOrWithDrawRequest request){
        return ResponseEntity.ok(accountService.depositMoney(request));
    }
    @PutMapping("/transfer")
    public String startTransferProcess(@RequestBody @Valid TransferMoneyRequest request) {
        return transferMoneyService.startTransferProcess(request);
    }
}
