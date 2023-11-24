package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;

public record OrderItemResponse(
    Long id,
    String code,
    AccommodationResponse accommodation,
    RoomTypeResponse roomType,
    LocalDate checkinDate,
    LocalDate checkoutDate,
    Boolean isUsed
) {
    public OrderItemResponse(
        OrderItem orderItem,
        RoomType roomType,
        Accommodation accommodation
    ) {
        this(
            orderItem.getId(),
            orderItem.getCode(),
            new AccommodationResponse(accommodation),
            new RoomTypeResponse(roomType),
            orderItem.getCheckinDate(),
            orderItem.getCheckoutDate(),
            LocalDate.now().isAfter(orderItem.getCheckoutDate())
        );
    }
}
