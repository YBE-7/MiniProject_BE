package com.example.miniproject.domain.roomtype.dto.response;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.entity.RoomTypeImage;
import java.util.Arrays;
import java.util.List;

public record RoomTypeDetailResponse(
    Long id,
    String name,
    Integer capacity,
    String introduction,
    List<String> services,
    List<String> images
) {
    public RoomTypeDetailResponse(RoomType roomType) {
        this(
            roomType.getId(),
            roomType.getName(),
            roomType.getCapacity(),
            roomType.getIntroduction(),
            Arrays.stream(roomType.getService()
                .split(","))
                .toList(),
            roomType.getImages()
                .stream()
                .map(RoomTypeImage::getUrl)
                .toList()
        );
    }
}
