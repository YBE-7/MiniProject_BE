package com.example.miniproject.domain.order.dto.response;

import com.example.miniproject.domain.order.entity.Order;

public record ClientResponse(
    String name,
    String phoneNumber
) {
    public ClientResponse(Order order) {
        this(
            order.getClientName(),
            order.getClientPhoneNumber()
        );
    }
}
