package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.dto.CustomerDto;
import com.bsuir.sdtt.entity.Customer;
import com.bsuir.sdtt.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of Customer REST Controller. Contains CRUD methods.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/v1/customer-management/customers")
public class CustomerController {
    /**
     * Field of customer service.
     */
    private final CustomerService customerService;

    /**
     * Field of Model Mapper converter.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor that accepts a object CustomerService class.
     *
     * @param customerService object of CustomerService class
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.modelMapper = new ModelMapper();
    }

    /**
     * Method that converts DTO to class object and create it.
     *
     * @param customerDto data transfer object
     * @return created object of Customer class
     */
    @PostMapping
    public CustomerDto create(@Validated @RequestBody CustomerDto customerDto) {
        log.debug("In create customer controller method");
        Customer customer = new Customer();
        modelMapper.map(customerDto, customer);
        CustomerDto customerDtoTemp = new CustomerDto();
        modelMapper.map(customerService.create(customer, customerDto.getImage()), customerDtoTemp);
        return customerDtoTemp;
    }

    /**
     * Method that save updated object.
     *
     * @param customerDto updated customer that needs to save
     * @return updated and saved customer
     */
    @PutMapping
    public CustomerDto update(@Validated @RequestBody CustomerDto customerDto) {
        log.debug("In update customer controller method");
        Customer customer = new Customer();
        modelMapper.map(customerDto, customer);
        CustomerDto customerDtoTemp = new CustomerDto();
        modelMapper.map(customerService.update(customer, customerDto.getImage()), customerDtoTemp);
        return customerDtoTemp;

    }

    /**
     * Method that update user password
     *@param customerDto customer dto with updated password
     * @return customer with updated password
     */
    @PutMapping(headers = "action=updatePassword")
    public CustomerDto updatePassword(@Validated @RequestBody CustomerDto customerDto) {
        log.debug("In updatePassword customer controller method");
        Customer customer = new Customer();
        modelMapper.map(customerDto, customer);
        CustomerDto customerDtoTemp = new CustomerDto();
        modelMapper.map(customerService.updatePassword(customer), customerDtoTemp);
        return customerDtoTemp;
    }

    /**
     * Method that finds an object.
     *
     * @param id UUID of the object to be found
     * @return founded object or NullPointerException
     */
    @GetMapping(path = "/{id}")
    public CustomerDto getById(@PathVariable("id") UUID id) {
        log.debug("In getById customer controller method");
        CustomerDto customerDtoTemp = new CustomerDto();
        modelMapper.map(customerService.findById(id), customerDtoTemp);
        return customerDtoTemp;
    }

    /**
     * Method that finds all objects.
     *
     * @return founded objects
     */
    @GetMapping
    public List<CustomerDto> getAll() {
        log.debug("In getAll customer controller method");
        List<CustomerDto> customersDtoTemp = new ArrayList<>();
        List<Customer> customers = customerService.findAll();
        toCustomersDtoList(customers, customersDtoTemp);
        return customersDtoTemp;
    }

    /**
     * Method that delete object.
     *
     * @param id object that needs to delete
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") UUID id) {
        log.debug("In delete customer controller method");
        customerService.delete(id);
    }

    private void toCustomersDtoList(List<Customer> customers,
                                    List<CustomerDto> customersDto) {
        for (Customer customer : customers) {
            CustomerDto customerDto = new CustomerDto();
            modelMapper.map(customer, customerDto);
            customersDto.add(customerDto);
        }
    }
}
