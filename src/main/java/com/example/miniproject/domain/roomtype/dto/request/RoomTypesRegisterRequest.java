package com.example.miniproject.domain.roomtype.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record RoomTypesRegisterRequest(

    @NotEmpty
    List<@Valid RoomTypeRegisterRequest> data
) {

}
