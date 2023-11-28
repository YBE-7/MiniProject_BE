package com.example.miniproject.domain.order.dto.request;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.global.constant.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRegisterRequest(

    @NotEmpty
    List<@Valid OrderItemRegisterRequest> orderItems,

    @NotNull
    @Valid
    ClientRequest client,

    @NotNull
    @Valid
    SubscriberRequest subscriber,

    @NotNull
    PaymentMethod paymentMethod
) {
    public Order toEntity(Member member, String code) {
        return Order.create(
            member,
            this.client.name(),
            this.client.phoneNumber(),
            this.subscriber.name(),
            this.subscriber().phoneNumber(),
            this.paymentMethod,
            code
        );
    }
}
