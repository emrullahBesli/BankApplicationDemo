package com.besliBank.bankapplication.dto.converter;

import com.besliBank.bankapplication.dto.AccountCustomerDto;
import com.besliBank.bankapplication.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class AccountCustomerDtoConverter {
    public static AccountCustomerDto accountCustomerDtoConverter(Customer customer){
        return new AccountCustomerDto(customer.getName(),customer.getSurName(),customer.getEmail(),customer.getTc());
    }

}
