package com.example.miniproject.domain.room.dto.request;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.room.entity.Room;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record RoomRegisterRequest(
    @NotNull
    Long accommodationId,

    @NotNull
    @Min(0)
    Integer price,

    @NotBlank
    String name,

    @Min(1)
    Integer capacity,

    @NotBlank
    String introduction,

    @NotNull
    @Min(0)
    Integer stock,

    @NotNull
    LocalDate startDate,

    @NotNull
    LocalDate endDate,

    //추후 List<Multipart>로 교체
    @NotNull
    List<String> images
) {
    public Room toEntity(
        Accommodation accommodation
    ) {
        return Room.create(
            accommodation,
            price,
            name,
            capacity,
            introduction,
            stock,
            startDate,
            endDate
        );
    }
}
