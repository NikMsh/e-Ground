package com.bsuir.repository;

import com.bsuir.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Customer findByEmail(String email);

    Customer findByPhoneNumber(String phoneNumber);
}
