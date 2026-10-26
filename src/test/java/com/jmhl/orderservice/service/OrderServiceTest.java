package com.jmhl.orderservice.service;

import com.jmhl.orderservice.dto.CreateOrderRequest;
import com.jmhl.orderservice.dto.OrderResponse;
import com.jmhl.orderservice.exception.OrderNotFoundException;
import com.jmhl.orderservice.model.Order;
import com.jmhl.orderservice.model.OrderStatus;
import com.jmhl.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {

        CreateOrderRequest request =
                new CreateOrderRequest(
                        "Moises",
                        new BigDecimal("149.99")
                );

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        OrderResponse response =
                orderService.createOrder(request);

        assertEquals("Moises", response.customerName());
        assertEquals(
                new BigDecimal("149.99"),
                response.totalAmount()
        );
        assertEquals(
                OrderStatus.CREATED,
                response.status()
        );

        verify(orderRepository)
                .save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenOrderDoesNotExist() {

        when(orderRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrder(999L)
        );

        verify(orderRepository)
                .findById(999L);
    }
}