package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.dto.OrderDto;
import com.bsuir.sdtt.entity.Order;
import com.bsuir.sdtt.service.OrderService;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of Order REST Controller. Contains CRUD methods.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/v1/inventory/orders")
public class OrderController {
    /**
     * Field of Order Service.
     */
    private final OrderService orderService;

    /**
     * Field of Model Mapper converter.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor that accepts a object OrderService class.
     *
     * @param orderService object of OrderService class
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.modelMapper = new ModelMapper();
    }

    /**
     * Method that converts DTO to class object and create it.
     *
     * @param orderDto data transfer object
     * @return created object of Order class
     */
    @PostMapping
    public OrderDto create(@ApiParam(name = "date", value = "Example date: 2018-12-12T15:15:15") @Validated @RequestBody OrderDto orderDto) {
        Order order = new Order();
        modelMapper.map(orderDto, order);
        OrderDto orderDtoTemp = new OrderDto();
        modelMapper.map(orderService.create(order), orderDtoTemp);
        return orderDtoTemp;
    }

    /**
     * Method that finds an object.
     *
     * @param id UUID of the object to be found
     * @return founded object or NullPointerException
     */
    @GetMapping(path = "/{id}")
    public OrderDto getById(@PathVariable("id") UUID id) {
        OrderDto orderDtoTemp = new OrderDto();
        modelMapper.map(orderService.findById(id), orderDtoTemp);
        return orderDtoTemp;
    }

    @GetMapping(path = "/customers/{customerId}")
    public List<OrderDto> getByIdCustomerId(@PathVariable("customerId") UUID customerId) {
        List<OrderDto> orderDtoTemp = new ArrayList<>();
        toOrdersDtoList(orderService.findByCustomerId(customerId), orderDtoTemp);
        return orderDtoTemp;
    }

    /**
     * Method that finds all objects.
     *
     * @return founded objects
     */
    @GetMapping
    public List<OrderDto> getAll() {
        List<OrderDto> ordersDtoTemp = new ArrayList<>();
        List<Order> orders = orderService.findAll();
        toOrdersDtoList(orders, ordersDtoTemp);
        return ordersDtoTemp;
    }

    /**
     * Method that save updated object.
     *
     * @param orderDto updated order that needs to save
     * @return updated and saved order
     */
    @PutMapping
    public OrderDto update(@Validated @RequestBody OrderDto orderDto) {
        Order order = new Order();
        modelMapper.map(orderDto, order);
        OrderDto orderDtoTemp = new OrderDto();
        modelMapper.map(orderService.update(order), orderDtoTemp);
        return orderDtoTemp;
    }

    /**
     * Method that delete object.
     *
     * @param id object that needs to delete
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") UUID id) {
        orderService.delete(id);
    }

    private void toOrdersDtoList(List<Order> orders, List<OrderDto> ordersDto) {
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();
            modelMapper.map(order, orderDto);
            ordersDto.add(orderDto);
        }
    }
}
