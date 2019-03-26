package com.bsuir.sdtt.service.impl;

import com.bsuir.sdtt.entity.Customer;
import com.bsuir.sdtt.repository.CustomerRepository;
import com.bsuir.sdtt.service.CustomerService;
import com.bsuir.sdtt.validation.CustomerValidator;
import com.bsuir.sdtt.validation.ValidationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Class of customer service that allows you to work with a customer and implements CustomerService.
 *
 * @author Stsiapan Balashenka, Eugene Korenik
 * @version 1.1
 */
@Service
@Transactional
public class DefaultCustomerService implements CustomerService {
    /**
     * Field of customer repository.
     */
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final CustomerValidator customerValidator;

    /**
     * Constructor that accepts a object CustomerDao class.
     *
     * @param customerRepository object of CustomerRepository class
     */
    @Autowired
    public DefaultCustomerService(CustomerRepository customerRepository,
                                  PasswordEncoder passwordEncoder,
                                  CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerValidator = customerValidator;
    }

    /**
     * Method that save Customer in database.
     *
     * @param customer object that needs to save
     * @return saved object of Customer class
     */
    @Override
    public Customer create(Customer customer) throws EntityExistsException {
        String errorMessage = customerValidator.isValid(customer, ValidationType.FOR_CREATING);
        if(!errorMessage.isEmpty()) {
            throw new EntityExistsException(errorMessage);
        }

        String password = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(password);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Method that finds an object in database.
     *
     * @param id UUID of the object to be found
     * @return founded object or NullPointerException
     */
    @Override
    public Customer findById(UUID id) throws EntityNotFoundException {
        return customerRepository.findById(id).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Customer with id =" + id.toString()
                    + " not found");});
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
    public Customer update(Customer customer) throws EntityExistsException {
        String errorMessage = customerValidator.isValid(customer, ValidationType.FOR_UPDATING);
        if(!errorMessage.isEmpty()) {
            throw new EntityExistsException(errorMessage);
        }
        return customerRepository.save(customer);
    }

    /**
     * Method that update customer password
     *
     * @param customer customer with updated password needs to save
     * @return updated and saved customer
     */
    public Customer updatePassword(Customer customer) {
        String password = customer.getPassword();
        String newPassword = passwordEncoder.encode(password);
        customer.setPassword(newPassword);
        return customerRepository.save(customer);
    }

    /**
     * Method that delete object.
     *
     * @param id object that needs to delete
     */
    @Override
    public void delete(UUID id) throws EntityNotFoundException {
        customerRepository.delete(findById(id));
    }
}
