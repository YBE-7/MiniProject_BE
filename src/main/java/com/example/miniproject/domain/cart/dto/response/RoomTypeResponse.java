package com.example.miniproject.domain.cart.dto.response;

import com.example.miniproject.domain.roomtype.entity.RoomType;

public record RoomTypeResponse(
    Long id,
    String name,
    Integer price,
    Integer capacity
) {
    public RoomTypeResponse(RoomType roomType) {
        this(
            roomType.getId(),
            roomType.getName(),
            roomType.getPrice(),
            roomType.getCapacity()
        );
    }
}
