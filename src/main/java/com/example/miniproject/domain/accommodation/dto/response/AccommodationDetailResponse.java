package com.example.miniproject.domain.accommodation.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.AccommodationImage;
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
        Accommodation accommodation
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
            accommodation.getImages()
                .stream()
                .map(AccommodationImage::getUrl)
                .toList()
        );
        images.add(0, accommodation.getThumbnailUrl());
    }
}
