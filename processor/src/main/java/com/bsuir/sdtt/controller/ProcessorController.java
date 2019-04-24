package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.client.CustomerManagementClient;
import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.catalog.CommentDto;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.dto.customer.ConversationDTO;
import com.bsuir.sdtt.dto.customer.CustomerDTO;
import com.bsuir.sdtt.dto.customer.MessageDTO;
import com.bsuir.sdtt.dto.favourite.OrderDto;
import com.bsuir.sdtt.dto.processor.AddCommentToOfferParameterDto;
import com.bsuir.sdtt.dto.processor.CreateOrderParameterDto;
import com.bsuir.sdtt.dto.processor.CustomerCommentParameterDto;
import com.bsuir.sdtt.service.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

/**
 * Class of Processor REST Controller.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/v1/processor")
public class ProcessorController {

    private final ProcessorService processorService;

    private final CustomerManagementClient client;

    @Autowired
    public ProcessorController(ProcessorService processorService,
                               CustomerManagementClient client) {
        this.processorService = processorService;
        this.client = client;
    }

    @PostMapping(path = "/orders")
    public OrderDto addToFavorites(@Validated @RequestBody
                                           CreateOrderParameterDto createOrderParameterDto) {
        return processorService.addToFavorite(createOrderParameterDto);
    }

    @PostMapping(path = "/customers")
    public CustomerDTO createCustomer(@Validated @RequestBody
                                              CustomerDTO customerDto) {
        return processorService.createCustomer(customerDto);
    }

    @PostMapping(path = "/offers")
    public OfferDto createOffer(@Validated @RequestBody OfferDto offerDto) {
        return processorService.createOffer(offerDto);
    }

    @PutMapping(path = "/customers")
    public CustomerDTO updateCustomer(@Validated @RequestBody
                                              CustomerDTO customerDto) {
        return processorService.updateCustomer(customerDto);
    }

    @PutMapping(path = "/offers")
    public OfferDto updateOffer(@Validated @RequestBody OfferDto offerDto) {
        return processorService.updateOffer(offerDto);
    }

    @PutMapping(path = "/offers/comments")
    public CustomerCommentParameterDto addCommentToOffer(
            @Validated @RequestBody AddCommentToOfferParameterDto addCommentToOfferParameterDto) {
        return processorService.addCommentToOffer(addCommentToOfferParameterDto);
    }

    @GetMapping(path = "/offers/comments/{id}")
    public List<CommentDto> getAllCommentsByOfferId(@PathVariable("id") UUID id) {
        return processorService.getAllCommentsByOfferId(id);
    }

    @GetMapping(path = "/offers/filter")
    public List<OfferDto> getOffersByFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "priceFrom", required = false) String priceFrom,
            @RequestParam(value = "priceTo", required = false) String priceTo) {
        return processorService.getOffersByFilter(name, category, priceFrom, priceTo);
    }

    @GetMapping(path = "/offers/{id}")
    public OfferDto getOfferById(@PathVariable("id") UUID id) {
        return processorService.getOfferById(id);
    }

    @GetMapping(path = "/customers/{id}")
    public CustomerDTO getCustomersById(@PathVariable("id") UUID id) {
        return processorService.getCustomerById(id);
    }

    @GetMapping(path = "/orders/{id}")
    public List<OrderDto> getOrderByCustomerId(@PathVariable("id") UUID id) {
        return processorService.getOrderByCustomerId(id);
    }

    @GetMapping(path = "/categories}")
    public List<CategoryDto> getAllCategories() {
        return processorService.getAllCategories();
    }

    @GetMapping(path = "/messages")
    public ConversationDTO getConversationInfo(@RequestParam("ourId") UUID yourId,
                                               @RequestParam("otherId") UUID otherId) {
       return client.getConversationInfo(yourId, otherId);
    }

    @GetMapping(path = "/conversations/{id}")
    public List<MessageDTO> getConversationMessages(@PathVariable("id") UUID id) {
        return client.getConversationMessages(id);
    }

    @GetMapping(path = "/conversations/users/{id}")
    public List<ConversationDTO> getConversationsByUserId(@PathVariable("id") UUID id) {
        return client.getConversatoinsByUserId(id);
    }
}
