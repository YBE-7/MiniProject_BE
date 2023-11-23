package com.example.miniproject.domain.order.dto.request;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.order.entity.Order;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
    List<OrderItemRequest> orderItems,
    @NotNull String clientName,
    @NotNull String clientPhoneNumber,
    @NotNull String subscriberName,
    @NotNull String subscriberPhoneNumber,
    @NotNull String paymentMethod
) {
    public Order toOrder(Member member) {

        return Order.create(
            member,
            this.clientName,
            this.clientPhoneNumber,
            this.subscriberName,
            this.subscriberPhoneNumber,
            this.paymentMethod
        );
    }
}
