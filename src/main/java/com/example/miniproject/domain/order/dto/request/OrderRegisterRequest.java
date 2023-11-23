package com.example.miniproject.domain.order.dto.request;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.order.entity.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRegisterRequest(
    @NotNull
    @Valid
    List<OrderItemRegisterRequest> orderItems,

    @NotBlank
    String clientName,

    @NotBlank
    String clientPhoneNumber,

    @NotBlank
    String subscriberName,

    @NotBlank
    String subscriberPhoneNumber,

    @NotBlank
    String paymentMethod
) {
    public Order toEntity(Member member, String code) {
        return Order.create(
            member,
            this.clientName,
            this.clientPhoneNumber,
            this.subscriberName,
            this.subscriberPhoneNumber,
            this.paymentMethod,
            code
        );
    }
}
