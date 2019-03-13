package com.bsuir.sdtt.client;

import com.bsuir.sdtt.dto.customer.CustomerDto;
import com.bsuir.sdtt.util.CustomerManagementClientProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

/**
 * Class of Customer Management Client.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Component
@Slf4j
public class CustomerManagementClient {
    private final RestTemplate restTemplate;

    @Value("${customer-management.url}")
    private String baseUrl;

    @Autowired
    public CustomerManagementClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomerDto save(CustomerDto customerDto) {
        log.info("Start method CustomerManagementClient.save: {}", customerDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CUSTOMER_MANAGEMENT);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<CustomerDto> responseEntity = restTemplate
                .postForEntity(finalUrl.toString(),
                        customerDto, CustomerDto.class);

        log.info("Customer DTO: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public CustomerDto update(CustomerDto customerDto) {
        log.info("Start method CustomerManagementClient.update: {}", customerDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CUSTOMER_MANAGEMENT);

        log.info("Final URL: {}", finalUrl.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CustomerDto> entity = new HttpEntity<>(customerDto, headers);

        ResponseEntity<CustomerDto> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.PUT,
                        entity, CustomerDto.class);

        log.info("Customer DTO: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    public CustomerDto getCustomerDto(UUID id) {
        log.info("Start method InventoryClient.getCustomersDto ID = {}", id);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CUSTOMER_MANAGEMENT);
        finalUrl.append(id);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<CustomerDto> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        entity, CustomerDto.class);

        log.info("Customer DTO: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }
}
