package com.example.miniproject.domain.order.dto.request;

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
    public OrderItem toEntity(Room room, String code) {
        return OrderItem.create(
            room,
            this.checkinDate,
            this.checkoutDate,
            code
        );
    }
}
