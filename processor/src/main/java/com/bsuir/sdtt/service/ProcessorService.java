package com.bsuir.sdtt.service;

import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.catalog.CommentDto;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.dto.customer.ConversationDTO;
import com.bsuir.sdtt.dto.customer.CustomerDTO;
import com.bsuir.sdtt.dto.favourite.OrderDto;
import com.bsuir.sdtt.dto.processor.AddCommentToOfferParameterDto;
import com.bsuir.sdtt.dto.processor.CreateOrderParameterDto;
import com.bsuir.sdtt.dto.processor.CustomerCommentParameterDto;

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

    CustomerDTO createCustomer(CustomerDTO customerDto);

    CustomerDTO updateCustomer(CustomerDTO customerDto);

    OfferDto createOffer(OfferDto offerDto);

    OfferDto updateOffer(OfferDto offerDto);

    CustomerCommentParameterDto addCommentToOffer(AddCommentToOfferParameterDto addCommentToOfferDto);

    List<CommentDto> getAllCommentsByOfferId(UUID id);

    CustomerDTO getCustomerById(UUID id);

    OfferDto getOfferById(UUID id);

    List<OfferDto> getOffersByFilter(String name, String category,
                                     String priceFrom, String priceTo);

    List<OrderDto> getOrderByCustomerId(UUID id);

    List<CategoryDto> getAllCategories();

    List<ConversationDTO> getConversationsByUserId(UUID id);

    ConversationDTO getConversationInfo(UUID yourId, UUID otherId);
}
