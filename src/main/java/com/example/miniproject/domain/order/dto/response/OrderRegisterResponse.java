package com.example.miniproject.domain.order.dto.response;

import com.example.miniproject.domain.order.entity.Order;

public record OrderRegisterResponse(
    Long orderId
) {
    public OrderRegisterResponse(Order order) {
        this(order.getId());
    }
}
