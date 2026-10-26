package com.jmhl.orderservice.dto;

import com.jmhl.orderservice.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull(message = "Status is required")
        OrderStatus status
) {
}
