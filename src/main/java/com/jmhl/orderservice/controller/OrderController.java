package com.jmhl.orderservice.controller;

import com.jmhl.orderservice.dto.CreateOrderRequest;
import com.jmhl.orderservice.dto.OrderResponse;
import com.jmhl.orderservice.dto.UpdateOrderStatusRequest;
import com.jmhl.orderservice.model.OrderStatus;
import com.jmhl.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @RequestParam(required = false) OrderStatus status) {

        return ResponseEntity.ok(
                orderService.getOrders(status)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(
                orderService.updateStatus(id, request.status())
        );
    }

    @GetMapping("/health")
    public String health() {
        return "Order Service is running";
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse response = orderService.createOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(orderService.getOrder(id));
    }
}