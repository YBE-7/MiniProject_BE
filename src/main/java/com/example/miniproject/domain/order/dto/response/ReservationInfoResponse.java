package com.example.miniproject.domain.order.dto.response;

import com.example.miniproject.domain.order.entity.Order;

public record ReservationInfoResponse(
    String code,
    String paymentMethod
) {
    public ReservationInfoResponse(Order order) {
        this(
            order.getCode(),
            order.getPaymentMethod()
        );
    }
}
