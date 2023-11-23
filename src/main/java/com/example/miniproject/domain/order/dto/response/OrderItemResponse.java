package com.example.miniproject.domain.order.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;

public record OrderItemResponse(
    String code,
    String accommodationName,
    String accommodationImage,
    String roomTypeName,
    LocalDate checkinDate,
    LocalDate checkoutDate,
    Integer capacity
) {
    public OrderItemResponse(
        OrderItem orderItem,
        RoomType roomType,
        Accommodation accommodation
    ) {
        this(
            orderItem.getCode(),
            accommodation.getName(),
            accommodation.getThumbnailUrl(),
            roomType.getName(),
            orderItem.getCheckinDate(),
            orderItem.getCheckoutDate(),
            roomType.getCapacity()
        );
    }
}
