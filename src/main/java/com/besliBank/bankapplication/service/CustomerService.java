package com.besliBank.bankapplication.service;


import com.besliBank.bankapplication.dto.CustomerDto;
import com.besliBank.bankapplication.dto.CustomerRequest;
import com.besliBank.bankapplication.dto.converter.CustomerDtoConverter;
import com.besliBank.bankapplication.exception.CustomerNotFoundException;
import com.besliBank.bankapplication.exception.DuplicateTCNoException;
import com.besliBank.bankapplication.model.Customer;
import com.besliBank.bankapplication.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }

    public CustomerDto addCustomer(CustomerRequest request) {
        if (customerRepository.existsByTc(request.getTc())) {
            throw new DuplicateTCNoException("Wrong Tc Number");
        }
        Customer customer = new Customer(request.getName(), request.getSurName(), request.getEmail(), request.getTc());
        customerRepository.save(customer);
        return new CustomerDtoConverter().customerDtoConvert(customer);
    }

    public Customer getCustomerByTc(String tc) {
        return customerRepository.getByTc(tc).orElseThrow(() -> new CustomerNotFoundException("Customer Could not found by tc " + tc));
    }


}
