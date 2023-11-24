package com.example.miniproject.domain.roomtype.dto.request;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RoomTypeRegisterRequest(
    @NotNull
    Long accommodationId,

    @NotBlank
    String name,

    @NotNull
    @Min(0)
    Integer price,

    @Min(1)
    Integer capacity,

    String introduction,

    @NotBlank
    String service,

    @NotNull
    List<String> images
) {
    public RoomType toEntity(
        Accommodation accommodation
    ) {
        return RoomType.create(
            accommodation,
            name,
            price,
            capacity,
            introduction,
            service
        );
    }
}
