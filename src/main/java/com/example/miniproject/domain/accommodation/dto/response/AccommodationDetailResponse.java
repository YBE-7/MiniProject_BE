package com.example.miniproject.domain.accommodation.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import java.util.Arrays;
import java.util.List;

public record AccommodationDetailResponse(
    Long id,
    String name,
    Double star,
    LocationResponse location,
    String introduction,
    List<String> services,
    List<String> images
) {
    public AccommodationDetailResponse(
        Accommodation accommodation,
        List<String> images
    ) {
        this(
            accommodation.getId(),
            accommodation.getName(),
            accommodation.getStar(),
            new LocationResponse(accommodation.getLocation()),
            accommodation.getIntroduction(),
            Arrays.stream(accommodation.getService()
                .split(","))
                .toList(),
            images
        );
    }
}
