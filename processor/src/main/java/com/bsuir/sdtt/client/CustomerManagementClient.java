package com.bsuir.sdtt.client;

import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.customer.ConversationDTO;
import com.bsuir.sdtt.dto.customer.CustomerDTO;
import com.bsuir.sdtt.dto.customer.MessageDTO;
import com.bsuir.sdtt.util.CustomerManagementClientProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    public CustomerDTO save(CustomerDTO customerDto) {
        log.info("Start method CustomerManagementClient.save: {}", customerDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CUSTOMER_MANAGEMENT);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<CustomerDTO> responseEntity = restTemplate
                .postForEntity(finalUrl.toString(),
                        customerDto, CustomerDTO.class);

        log.info("Customer DTO: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public CustomerDTO update(CustomerDTO customerDto) {
        log.info("Start method CustomerManagementClient.update: {}", customerDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CUSTOMER_MANAGEMENT);

        log.info("Final URL: {}", finalUrl.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CustomerDTO> entity = new HttpEntity<>(customerDto, headers);

        ResponseEntity<CustomerDTO> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.PUT,
                        entity, CustomerDTO.class);

        log.info("Customer DTO: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    public CustomerDTO getCustomerDto(UUID id) {
        log.info("Start method InventoryClient.getCustomersDto ID = {}", id);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CUSTOMER_MANAGEMENT);
        finalUrl.append(id);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<CustomerDTO> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        getHttpEntityHeader(), CustomerDTO.class);

        log.info("Customer DTO: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    public List<ConversationDTO> getConversatoinsByUserId(UUID id) {
        log.info("Start method InventoryClient.getConversationsByUserId");

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CONVERSATIONS);
        finalUrl.append(id);

        ResponseEntity<ConversationDTO[]> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        getHttpEntityHeader(), ConversationDTO[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    public ConversationDTO getConversationInfo(UUID yourId, UUID otherId) {
        log.info("Start method InventoryClient.getCustomersDto ID = {}");

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_CONVERSATIONS);
        finalUrl.append("?");
        finalUrl.append("yourId=");
        finalUrl.append(yourId);
        finalUrl.append("&otherId=");
        finalUrl.append(otherId);

        ResponseEntity<ConversationDTO> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                getHttpEntityHeader(), ConversationDTO.class);

        return responseEntity.getBody();
    }

    public List<MessageDTO> getConversationMessages(UUID conversationId) {
        log.info("Start method InventoryClient.getConversationsByUserId");

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CustomerManagementClientProperty.API_V1_MESSAGES);
        finalUrl.append(conversationId);

        ResponseEntity<MessageDTO[]> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        getHttpEntityHeader(), MessageDTO[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    private HttpEntity<String> getHttpEntityHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        return entity;
    }
}
