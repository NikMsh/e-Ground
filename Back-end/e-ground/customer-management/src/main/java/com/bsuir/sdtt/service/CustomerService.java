package com.bsuir.sdtt.service;

import com.bsuir.sdtt.entity.Customer;

import java.util.List;
import java.util.UUID;


/**
 * Class of customer service that allows you to work with offers and implements CustomerService.
 *
 * @author Stsiapan Balashenka, Eugene Korenik
 * @version 1.1
 */
public interface CustomerService {

    Customer create(Customer customer);

    Customer findById(UUID id);

    List<Customer> findAll();

    Customer update(Customer customer);

    Customer updatePassword(Customer customer);

    void delete(UUID id);
}
