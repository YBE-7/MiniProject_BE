package com.example.miniproject.domain.roomtype.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoomTypeSearchCondition {
    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate to;

    @NotNull
    private Integer capacity;
}
