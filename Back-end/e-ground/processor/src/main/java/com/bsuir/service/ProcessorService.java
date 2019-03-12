package com.bsuir.service;

import com.bsuir.dto.catalog.OfferDto;
import com.bsuir.dto.customer.CustomerDto;
import com.bsuir.dto.inventory.OrderDto;
import com.bsuir.dto.processor.CreateOrderParameterDto;

import java.util.List;
import java.util.UUID;

public interface ProcessorService {
    OrderDto addToFavorite(CreateOrderParameterDto createOrderParameter);

    CustomerDto createCustomer(CustomerDto customerDto);

    OfferDto createOffer(OfferDto offerDto);

    CustomerDto getCustomerById(UUID id);

    List<OfferDto> getOffersByFilter(String category, String priceFrom, String priceTo);

    List<OrderDto> getOrderByCustomerId(UUID id);

}
