package com.bsuir.service.impl;

import com.bsuir.entity.Customer;
import com.bsuir.exception.EntityNotFoundException;
import com.bsuir.repository.CustomerRepository;
import com.bsuir.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of customer service that allows you to work with a customer and implements CustomerService.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Service
@Transactional
public class DefaultCustomerService implements CustomerService {
    /**
     * Field of customer repository.
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor that accepts a object CustomerDao class.
     *
     * @param customerRepository object of CustomerRepository class
     */
    @Autowired
    public DefaultCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Method that save Customer in database.
     *
     * @param customer object that needs to save
     * @return saved object of Customer class
     */
    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Method that finds an object in database.
     *
     * @param id UUID of the object to be found
     * @return founded object or NullPointerException
     */
    @Override
    public Customer findById(UUID id) {
        return customerRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    /**
     * Method that finds all objects in database.
     *
     * @return founded objects
     */
    @Override
    public List<Customer> findAll() {
        Iterable<Customer> saveCustomers = customerRepository.findAll();
        List<Customer> createdCustomers = new ArrayList<>();
        for (Customer customer : saveCustomers) {
            createdCustomers.add(customer);
        }
        return createdCustomers;
    }

    /**
     * Method that save updated object.
     *
     * @param customer updated customer that needs to save
     * @return updated and saved customer
     */
    @Override
    public Customer update(Customer customer) {
        return create(customer);
    }

    /**
     * Method that delete object.
     *
     * @param id object that needs to delete
     */
    @Override
    public void delete(UUID id) {
        try {
            customerRepository.delete(findById(id));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
