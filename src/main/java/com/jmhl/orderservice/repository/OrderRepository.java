package com.jmhl.orderservice.repository;

import com.jmhl.orderservice.model.Order;
import com.jmhl.orderservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
}