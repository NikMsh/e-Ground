package com.bsuir.service.impl;

import com.bsuir.entity.Order;
import com.bsuir.exception.EntityNotFoundException;
import com.bsuir.repository.OrderRepository;
import com.bsuir.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of order service that allows you to work with a category and implements OrderItemService.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Service
@Transactional
public class DefaultOrderService implements OrderService {
    /**
     * Field of Order Repository.
     */
    private final OrderRepository orderRepository;

    /**
     * Constructor that accepts a object OrderRepository class.
     *
     * @param orderRepository object of OrderRepository class
     */
    @Autowired
    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Method that save Order in database.
     *
     * @param order object that needs to save
     * @return saved object of Order class
     */
    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Method that finds all objects in database.
     *
     * @return founded objects
     */
    @Override
    public List<Order> findAll() {
        Iterable<Order> saveOrders = orderRepository.findAll();
        List<Order> createdOrders = new ArrayList<>();
        for (Order order : saveOrders) {
            createdOrders.add(order);
        }
        return createdOrders;
    }

    /**
     * Method that finds an object in database.
     *
     * @param id Long of the object to be found
     * @return founded object or NullPointerException
     */
    @Override
    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<Order> findByCustomerId(UUID customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }
    /**
     * Method that save updated object.
     *
     * @param order updated order that needs to save
     * @return updated and saved order
     */
    @Override
    public Order update(Order order) {
        return create(order);
    }

    /**
     * Method that delete object.
     *
     * @param id object that needs to delete
     */
    @Override
    public void delete(UUID id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
