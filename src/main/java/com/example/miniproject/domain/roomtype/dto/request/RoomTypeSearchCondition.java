package com.example.miniproject.domain.roomtype.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RoomTypeSearchCondition(

    @NotNull
    LocalDate from,

    @NotNull
    LocalDate to,

    @NotNull
    Integer capacity
) {

}
