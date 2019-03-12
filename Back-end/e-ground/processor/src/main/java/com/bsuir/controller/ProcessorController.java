package com.bsuir.controller;

import com.bsuir.dto.catalog.OfferDto;
import com.bsuir.dto.customer.CustomerDto;
import com.bsuir.dto.inventory.OrderDto;
import com.bsuir.dto.processor.CreateOrderParameterDto;
import com.bsuir.service.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public OrderDto addToFavorites(@Validated @RequestBody CreateOrderParameterDto createOrderParameter) {
        return processorService.addToFavorite(createOrderParameter);
    }

    @PostMapping(path = "/customers")
    public CustomerDto createCustomer(@Validated @RequestBody CustomerDto customerDto) {
        return processorService.createCustomer(customerDto);
    }

    @PostMapping(path = "/offers")
    public OfferDto createOffer(@Validated @RequestBody OfferDto offerDto) {
        return processorService.createOffer(offerDto);
    }

    @GetMapping(path = "/offers/filter")
    public List<OfferDto> getOffersByFilter(@RequestParam(value = "category", required = false) String category,
                                            @RequestParam(value = "priceFrom", required = false) String priceFrom,
                                            @RequestParam(value = "priceTo", required = false) String priceTo) {
        return processorService.getOffersByFilter(category, priceFrom, priceTo);
    }

    @GetMapping(path = "/customers/{id}")
    public CustomerDto getCustomersById(@PathVariable("id") UUID id) {
        return processorService.getCustomerById(id);
    }

    @GetMapping(path = "/orders/{id}")
    public List<OrderDto> getOrderByCustomerId(@PathVariable("id") UUID id) {
        return processorService.getOrderByCustomerId(id);
    }
}
