package com.bsuir.service;

import com.bsuir.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer create(Customer customer);

    Customer findById(UUID id);

    List<Customer> findAll();

    Customer update(Customer customer);

    void delete(UUID id);
}
