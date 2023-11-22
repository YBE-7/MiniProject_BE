package com.example.miniproject.domain.roomtype.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RoomTypeSearchCondition(

    @NotNull
    LocalDate checkinDate,

    @NotNull
    LocalDate checkoutDate,

    @NotNull
    @Min(1)
    Integer capacity
) {

}
