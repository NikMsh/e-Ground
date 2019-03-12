package com.bsuir.client;

import com.bsuir.dto.catalog.OfferDto;
import com.bsuir.dto.inventory.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class InventoryClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryClient.class);

    private static final String API_V1_INVENTORY_ORDERS = "api/v1/inventory/orders";

    private static final String API_V1_INVENTORY_ORDERS_CUSTOMER_ID = "/api/v1/inventory/orders/customers/";

    private final RestTemplate restTemplate;

    @Value("${inventory.url}")
    private String baseUrl;

    @Autowired
    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderDto save(OrderDto orderDto) {
        LOGGER.info("Start method save InventoryClient.OrderDto: {}", orderDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(API_V1_INVENTORY_ORDERS);

        LOGGER.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<OrderDto> responseEntity = restTemplate.postForEntity(finalUrl.toString(), orderDto, OrderDto.class);

        LOGGER.info("Order DTO: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public List<OrderDto> getOrdersDto(UUID id) {
        LOGGER.info("Start method InventoryClient.getOrdersDto ID = {}", id);

        HttpHeaders headers = new HttpHeaders();
        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(API_V1_INVENTORY_ORDERS_CUSTOMER_ID);
        finalUrl.append(id);

        LOGGER.info("Final URL: {}", finalUrl.toString());

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<OrderDto[]> responseEntity = restTemplate.exchange(finalUrl.toString(), HttpMethod.GET, entity, OrderDto[].class);

        LOGGER.info("Size Orders DTO: {}", Objects.requireNonNull(responseEntity.getBody()).length);

        return Arrays.asList(responseEntity.getBody());
    }
}
