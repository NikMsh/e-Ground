package com.bsuir.sdtt.client;

import com.bsuir.sdtt.dto.favourite.OrderDto;
import com.bsuir.sdtt.util.FavouriteItemClientManagementProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Class of Favourite Item Management Client.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Component
@Slf4j
public class FavouriteItemManagementClient {
    private final RestTemplate restTemplate;

    @Value("${favourite-item-management.url}")
    private String baseUrl;

    @Autowired
    public FavouriteItemManagementClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderDto save(OrderDto orderDto) {
        log.info("Start method save InventoryClient.OrderDto: {}", orderDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(FavouriteItemClientManagementProperty.API_V1_INVENTORY_ORDERS);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<OrderDto> responseEntity = restTemplate
                .postForEntity(finalUrl.toString(), orderDto, OrderDto.class);

        log.info("Order DTO: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public List<OrderDto> getOrdersDto(UUID id) {
        log.info("Start method InventoryClient.getOrdersDto ID = {}", id);

        HttpHeaders headers = new HttpHeaders();
        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(FavouriteItemClientManagementProperty
                .API_V1_INVENTORY_ORDERS_CUSTOMER_ID);
        finalUrl.append(id);

        log.info("Final URL: {}", finalUrl.toString());

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<OrderDto[]> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        entity, OrderDto[].class);

        log.info("Size Orders DTO: {}", Objects
                .requireNonNull(responseEntity.getBody()).length);

        return Arrays.asList(responseEntity.getBody());
    }
}
