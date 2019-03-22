package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.catalog.CommentDto;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.dto.customer.CustomerDto;
import com.bsuir.sdtt.dto.favourite.OrderDto;
import com.bsuir.sdtt.dto.processor.AddCommentToOfferParameterDto;
import com.bsuir.sdtt.dto.processor.CreateOrderParameterDto;
import com.bsuir.sdtt.service.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @PostMapping(path = "/orders")
    public OrderDto addToFavorites(@Validated @RequestBody
                                           CreateOrderParameterDto createOrderParameterDto) {
        return processorService.addToFavorite(createOrderParameterDto);
    }

    @PostMapping(path = "/customers")
    public CustomerDto createCustomer(@Validated @RequestBody
                                              CustomerDto customerDto) {
        return processorService.createCustomer(customerDto);
    }

    @PostMapping(path = "/offers")
    public OfferDto createOffer(@Validated @RequestBody OfferDto offerDto) {
        return processorService.createOffer(offerDto);
    }

    @PutMapping(path = "/customers")
    public CustomerDto updateCustomer(@Validated @RequestBody
                                              CustomerDto customerDto) {
        return processorService.updateCustomer(customerDto);
    }

    @PutMapping(path = "/offers")
    public OfferDto updateOffer(@Validated @RequestBody OfferDto offerDto) {
        return processorService.updateOffer(offerDto);
    }

    @PutMapping(path = "/offers/comments")
    public OfferDto addCommentToOffer(
            @Validated @RequestBody AddCommentToOfferParameterDto addCommentToOfferParameterDto) {
        return processorService.addCommentToOffer(addCommentToOfferParameterDto);
    }

    @GetMapping(path = "/offers/comments/{id}")
    public List<CommentDto> getAllCommentsByOfferId(@PathVariable("id") UUID id) {
        return processorService.getAllCommentsByOfferId(id);
    }

    @GetMapping(path = "/offers/filter")
    public List<OfferDto> getOffersByFilter(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "priceFrom", required = false) String priceFrom,
            @RequestParam(value = "priceTo", required = false) String priceTo) {
        return processorService.getOffersByFilter(category, priceFrom, priceTo);
    }

    @GetMapping(path = "/offers/{id}")
    public OfferDto getOfferById(@PathVariable("id") UUID id) {
        return processorService.getOfferById(id);
    }

    @GetMapping(path = "/customers/{id}")
    public CustomerDto getCustomersById(@PathVariable("id") UUID id) {
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
}
