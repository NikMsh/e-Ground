package com.bsuir.sdtt.service;

import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.dto.customer.CustomerDto;
import com.bsuir.sdtt.dto.favourite.OrderDto;
import com.bsuir.sdtt.dto.processor.CreateOrderParameterDto;

import java.util.List;
import java.util.UUID;

/**
 * Class of processor service.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
public interface ProcessorService {
    OrderDto addToFavorite(CreateOrderParameterDto createOrderParameter);

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(CustomerDto customerDto);

    OfferDto createOffer(OfferDto offerDto);

    OfferDto updateOffer(OfferDto offerDto);

    CustomerDto getCustomerById(UUID id);

    OfferDto getOfferById(UUID id);

    List<OfferDto> getOffersByFilter(String category,
                                     String priceFrom, String priceTo);

    List<OrderDto> getOrderByCustomerId(UUID id);

    List<CategoryDto> getAllCategories();
}
