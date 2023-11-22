package com.example.miniproject.domain.room.dto.response;

import com.example.miniproject.domain.room.entity.Room;

public record RoomRegisterResponse(
    Long roomId
) {
    public RoomRegisterResponse(Room room) {
        this(room.getId());
    }
}
