package com.example.miniproject.domain.roomtype.dto.response;

import com.example.miniproject.domain.roomtype.entity.RoomType;

public record RoomTypeRegisterResponse(
    Long roomTypeId
) {
    public RoomTypeRegisterResponse(RoomType roomType) {
        this(roomType.getId());
    }
}
