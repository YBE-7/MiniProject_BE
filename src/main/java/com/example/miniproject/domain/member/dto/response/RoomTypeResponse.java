package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.roomtype.entity.RoomType;

public record RoomTypeResponse(
    Long id,
    String name,
    Integer capacity
) {
    public RoomTypeResponse(RoomType roomType) {
        this(
            roomType.getId(),
            roomType.getName(),
            roomType.getCapacity()
        );
    }
}
