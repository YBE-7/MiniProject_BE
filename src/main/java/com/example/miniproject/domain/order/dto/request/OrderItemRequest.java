package com.example.miniproject.domain.order.dto.request;

import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.room.entity.Room;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderItemRequest(
    @NotNull Long roomTypeId,
    @NotNull LocalDate checkinDate,
    @NotNull LocalDate checkoutDate) {
    public OrderItem toOrderItem(Room room) {
        return OrderItem.create(
            room,
            this.checkinDate,
            this.checkoutDate
        );
    }
}
