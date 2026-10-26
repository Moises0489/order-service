package com.jmhl.orderservice.service;

import com.jmhl.orderservice.dto.CreateOrderRequest;
import com.jmhl.orderservice.dto.OrderResponse;
import com.jmhl.orderservice.exception.OrderNotFoundException;
import com.jmhl.orderservice.model.Order;
import com.jmhl.orderservice.model.OrderStatus;
import com.jmhl.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order(
                request.customerName(),
                request.totalAmount(),
                OrderStatus.CREATED
        );

        Order savedOrder = orderRepository.save(order);

        return toResponse(savedOrder);
    }

    public OrderResponse getOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));

        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    public List<OrderResponse> getOrders(OrderStatus status) {

        List<Order> orders = status == null
                ? orderRepository.findAll()
                : orderRepository.findByStatus(status);

        return orders.stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse updateStatus(
            Long id,
            OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id));

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return toResponse(updatedOrder);
    }
}