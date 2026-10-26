package com.jmhl.orderservice.dto;

import com.jmhl.orderservice.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String customerName,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt
) {
}