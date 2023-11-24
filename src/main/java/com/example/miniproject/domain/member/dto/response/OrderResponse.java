package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.order.entity.Order;
import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
    Long id,
    LocalDate orderDate,
    List<OrderItemResponse> orderItems
) {
    public OrderResponse(
        Order order,
        List<OrderItemResponse> orderItems
    ) {
        this(
            order.getId(),
            order.getCreatedAt().toLocalDate(),
            orderItems
        );
    }
}
