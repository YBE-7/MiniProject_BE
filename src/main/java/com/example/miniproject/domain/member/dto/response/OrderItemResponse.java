package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;

public record OrderItemResponse(
    String accommodationImage,
    String accommodationName,
    String roomTypeName,
    LocalDate checkinDate,
    LocalDate checkoutDate,
    Integer capacity,
    Boolean isUsed
) {
    public OrderItemResponse(
        OrderItem orderItem,
        RoomType roomType,
        Accommodation accommodation
    ) {
        this(
            accommodation.getThumbnailUrl(),
            accommodation.getName(),
            roomType.getName(),
            orderItem.getCheckinDate(),
            orderItem.getCheckoutDate(),
            roomType.getCapacity(),
            LocalDate.now().isAfter(orderItem.getCheckoutDate())
        );
    }
}
