package com.besliBank.bankapplication.dto.converter;


import com.besliBank.bankapplication.dto.CustomerDto;
import com.besliBank.bankapplication.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {

    public CustomerDto customerDtoConvert(Customer customer) {
        return new  CustomerDto(customer.getName()
                , customer.getSurName()
                ,customer.getEmail()
                , customer.getTc()
               , Objects.requireNonNull(customer.getAccounts()).stream().map(CustomerAccountsDtoConverter::customerAccountsDtoConverter).collect(Collectors.toSet()));
    }
}
