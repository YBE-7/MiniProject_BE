package com.example.miniproject.domain.order.dto.response;

import com.example.miniproject.domain.order.entity.Order;

public record SubscriberResponse(
    String name,
    String phoneNumber
) {
    public SubscriberResponse(Order order) {
        this(
            order.getSubscriberName(),
            order.getSubscriberPhoneNumber()
        );
    }
}
