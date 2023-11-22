package com.example.miniproject.domain.room.dto.request;

import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomRegisterRequest(

    @NotNull
    Long roomTypeId,

    @NotBlank
    String name
) {
    public Room toEntity(
        RoomType roomType
    ) {
        return Room.create(
            roomType,
            name
        );
    }
}
