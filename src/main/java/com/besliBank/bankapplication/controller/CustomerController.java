package com.besliBank.bankapplication.controller;

import com.besliBank.bankapplication.dto.CustomerDto;
import com.besliBank.bankapplication.dto.CustomerRequest;
import com.besliBank.bankapplication.service.AccountService;
import com.besliBank.bankapplication.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/besliBank/customer")
public class CustomerController {
    private final CustomerService customerService;


    public CustomerController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;

    }

    @PostMapping()
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.addCustomer(request));
    }
}

