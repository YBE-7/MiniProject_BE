package com.example.miniproject.domain.roomtype.dto.request;

import java.util.List;

public record RoomTypesRegisterRequest(
    List<RoomTypeRegisterRequest> data
) {

}
