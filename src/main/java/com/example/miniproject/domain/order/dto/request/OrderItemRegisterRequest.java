package com.example.miniproject.domain.order.dto.request;

import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.room.entity.Room;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderItemRegisterRequest(
    @NotNull
    Long roomTypeId,

    @NotNull
    LocalDate checkinDate,

    @NotNull
    LocalDate checkoutDate
) {
    public OrderItem toEntity(Order order, Room room, String code) {
        return OrderItem.create(
            order,
            room,
            this.checkinDate,
            this.checkoutDate,
            code
        );
    }
}
