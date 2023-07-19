package com.besliBank.bankapplication.repository;

import com.besliBank.bankapplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
Optional<Customer>  getByTc(String tc);
Boolean existsByTc(String tc);
}
