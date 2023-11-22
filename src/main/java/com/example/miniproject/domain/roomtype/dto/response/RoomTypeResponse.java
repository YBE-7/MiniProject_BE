package com.example.miniproject.domain.roomtype.dto.response;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.entity.RoomTypeImage;
import java.util.List;

public record RoomTypeResponse(
    Long id,
    String name,
    Integer price,
    Integer capacity,
    List<String> images,
    Long stock
) {
    public RoomTypeResponse(
        RoomType roomType,
        Long stock
    ) {
        this(
            roomType.getId(),
            roomType.getName(),
            roomType.getPrice(),
            roomType.getCapacity(),
            roomType.getImages()
                .stream()
                .map(RoomTypeImage::getUrl)
                .toList(),
            stock
        );
    }
}
