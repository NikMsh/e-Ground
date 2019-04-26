package com.bsuir.sdtt.service.impl;

import com.bsuir.sdtt.client.CatalogClient;
import com.bsuir.sdtt.client.CustomerManagementClient;
import com.bsuir.sdtt.client.FavouriteItemManagementClient;
import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.catalog.CommentDto;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.dto.customer.ConversationDTO;
import com.bsuir.sdtt.dto.customer.CustomerDTO;
import com.bsuir.sdtt.dto.favourite.OrderDto;
import com.bsuir.sdtt.dto.processor.AccountDto;
import com.bsuir.sdtt.dto.processor.AddCommentToOfferParameterDto;
import com.bsuir.sdtt.dto.processor.CreateOrderParameterDto;
import com.bsuir.sdtt.dto.processor.CustomerCommentParameterDto;
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

        CustomerDTO customerDto = customerManagementClient.getCustomerDto(customerId);
        OfferDto offerDto = catalogClient.getOfferDto(itemId);

        log.info("Start method DefaultProcessorService.createOrder customerId = {}",
                customerId);

        OrderDto savedOrderDto = null;
        if (customerId.equals(customerDto.getId())) {
            orderDto.setCustomerId(customerId);
            orderDto.setName(customerDto.getFirstName());
            orderDto.setEmail(customerDto.getEmail());
            orderDto.setTotalPrice(offerDto.getPrice() * createOrderParameter
                    .getItemCount());
            orderDto.setOrderItemCount(createOrderParameter.getItemCount());
            orderDto.setImage(offerDto.getImage());
            orderDto.setImageId(offerDto.getImageId());
            orderDto.setCompressedImageId(offerDto.getCompressedImageId());
            savedOrderDto = favouriteItemManagementClient.save(orderDto);
        }

        return savedOrderDto;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDto) {
        log.debug("Start method DefaultProcessorService.createCustomer Customer DTO = {}", customerDto);
        return customerManagementClient.save(customerDto);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDto) {
        log.debug("Start method DefaultProcessorService.updateCustomer Customer DTO = {}", customerDto);
        return customerManagementClient.update(customerDto);
    }

    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        log.debug("Start method DefaultProcessorService.createOffer Offer DTO = {}", offerDto);
        OfferDto offerDtoCreated = null;
        if(customerManagementClient.getCustomerDto(offerDto.getSellerId())!=null){
            offerDtoCreated = catalogClient.save(offerDto);
        }
        return offerDtoCreated;
    }

    @Override
    public CustomerCommentParameterDto addCommentToOffer(AddCommentToOfferParameterDto addCommentToOfferDto) {
        log.debug("Start method DefaultProcessorService.addCommentToOffer" +
                " Customer DTO = {}", addCommentToOfferDto);

        CustomerDTO customerDto = customerManagementClient
                .getCustomerDto(addCommentToOfferDto.getCustomerId());
        CustomerCommentParameterDto customerCommentParameterDto = null;
        if (addCommentToOfferDto.getCustomerId().equals(customerDto.getId())) {
            CommentDto commentDto = CommentDto.builder()
                    .customerId(addCommentToOfferDto.getCustomerId())
                    .message(addCommentToOfferDto.getMessage())
                    .build();

            OfferDto offerDto = catalogClient.addCommentToOffer(addCommentToOfferDto.getOfferId(), commentDto);

            AccountDto accountDto = AccountDto.builder()
                    .id(customerDto.getId())
                    .name(customerDto.getFirstName())
                    .surname(customerDto.getLastName())
                    .age(customerDto.getAge())
                    .email(customerDto.getEmail())
                    .phoneNumber(customerDto.getPhoneNumber())
                    .build();

            customerCommentParameterDto = CustomerCommentParameterDto.builder()
                    .id(offerDto.getComments().get(offerDto.getComments().size() - 1).getId())
                    .message(addCommentToOfferDto.getMessage())
                    .accountDto(accountDto)
                    .build();
        }

        return customerCommentParameterDto;
    }

    @Override
    public OfferDto updateOffer(OfferDto offerDto) {
        log.debug("Start method DefaultProcessorService.updateOffer Offer DTO = {}", offerDto);
        return catalogClient.update(offerDto);
    }

    @Override
    public List<CommentDto> getAllCommentsByOfferId(UUID id) {
        log.debug("Start method DefaultProcessorService.getAllCommentsByOfferId ID = {}", id);

        return catalogClient.getAllCommentsByOfferId(id);
    }

    @Override
    public OfferDto getOfferById(UUID id) {
        log.info("Start method DefaultProcessorService.getOfferById ID = {}", id);
        return catalogClient.getOfferDto(id);
    }

    @Override
    public List<OfferDto> getOffersByFilter(String name, String category, String priceFrom, String priceTo) {
        log.info("Start method DefaultProcessorService.getOffersByFilter");

        return catalogClient.getOffersDtoByFilter(name, category, priceFrom, priceTo);
    }

    @Override
    public CustomerDTO getCustomerById(UUID id) {
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

    @Override
    public List<ConversationDTO> getConversationsByUserId(UUID userId) {
        return null;
    }

    @Override
    public ConversationDTO getConversationInfo(UUID yourId, UUID otherId) {
        return null;
    }
}
