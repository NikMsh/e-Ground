package com.bsuir.sdtt.service.impl;

import com.bsuir.sdtt.client.CatalogClient;
import com.bsuir.sdtt.client.CustomerManagementClient;
import com.bsuir.sdtt.client.FavouriteItemManagementClient;
import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.dto.customer.CustomerDto;
import com.bsuir.sdtt.dto.favourite.OrderDto;
import com.bsuir.sdtt.dto.processor.CreateOrderParameterDto;
import com.bsuir.sdtt.service.ProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Class of processor service.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Service
@Slf4j
public class DefaultProcessorService implements ProcessorService {
    private final CatalogClient catalogClient;

    private final FavouriteItemManagementClient favouriteItemManagementClient;

    private final CustomerManagementClient customerManagementClient;

    @Autowired
    public DefaultProcessorService(CatalogClient catalogClient,
                                   FavouriteItemManagementClient favouriteItemManagement,
                                   CustomerManagementClient customerManagementClient) {
        this.catalogClient = catalogClient;
        this.favouriteItemManagementClient = favouriteItemManagement;
        this.customerManagementClient = customerManagementClient;
    }

    @Override
    public OrderDto addToFavorite(CreateOrderParameterDto createOrderParameter) {
        OrderDto orderDto = new OrderDto();

        UUID customerId = createOrderParameter.getCustomerId();
        UUID itemId = createOrderParameter.getItemId();

        CustomerDto customerDto = customerManagementClient.getCustomerDto(customerId);
        OfferDto offerDto = catalogClient.getOfferDto(itemId);

        log.info("Start method DefaultProcessorService.createOrder customerId = {}",
                customerId);

        if (customerId.equals(customerDto.getId())) {
            orderDto.setCustomerId(customerId);
            orderDto.setName(customerDto.getName());
            orderDto.setEmail(customerDto.getEmail());
            orderDto.setTotalPrice(offerDto.getPrice() * createOrderParameter
                    .getItemCount());
            orderDto.setOrderItemCount(createOrderParameter.getItemCount());

            favouriteItemManagementClient.save(orderDto);
        }

        return orderDto;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        log.debug("Start method DefaultProcessorService.createCustomer Customer DTO = {}", customerDto);

        return customerManagementClient.save(customerDto);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        log.debug("Start method DefaultProcessorService.updateCustomer Customer DTO = {}", customerDto);

        return customerManagementClient.update(customerDto);
    }

    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        log.debug("Start method DefaultProcessorService.createOffer Offer DTO = {}", offerDto);

        return catalogClient.save(offerDto);
    }

    @Override
    public OfferDto updateOffer(OfferDto offerDto) {
        log.debug("Start method DefaultProcessorService.updateOffer Offer DTO = {}", offerDto);

        return catalogClient.update(offerDto);
    }

    @Override
    public OfferDto getOfferById(UUID id) {
        log.info("Start method DefaultProcessorService.getOfferById ID = {}", id);

        return catalogClient.getOfferDto(id);
    }

    @Override
    public List<OfferDto> getOffersByFilter(String category, String priceFrom, String priceTo) {
        log.info("Start method DefaultProcessorService.getOffersByFilter");

        return catalogClient.getOffersDtoByFilter(category, priceFrom, priceTo);
    }

    @Override
    public CustomerDto getCustomerById(UUID id) {
        log.info("Start method DefaultProcessorService.getCustomersByEmail ID = {}", id);

        return customerManagementClient.getCustomerDto(id);
    }

    @Override
    public List<OrderDto> getOrderByCustomerId(UUID id) {
        log.info("Start method DefaultProcessorService.getOrderById ID = {}", id);

        return favouriteItemManagementClient.getOrdersDto(id);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        log.info("Start method DefaultProcessorService.getAllCategories");

        return catalogClient.getAllCategories();
    }
}
