package com.jmhl.orderservice.controller;

import com.jmhl.orderservice.dto.OrderResponse;
import com.jmhl.orderservice.model.OrderStatus;
import com.jmhl.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void shouldReturnOrderById() throws Exception {

        OrderResponse response = new OrderResponse(
                1L,
                "Moises",
                new BigDecimal("149.99"),
                OrderStatus.CREATED,
                LocalDateTime.of(2026, 10, 25, 21, 0)
        );

        when(orderService.getOrder(1L))
                .thenReturn(response);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Moises"))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }
}