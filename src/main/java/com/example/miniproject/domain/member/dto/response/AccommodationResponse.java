package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.global.constant.AccommodationType;

public record AccommodationResponse(
    Long id,
    AccommodationType type,
    String name,
    String image
) {
    public AccommodationResponse(Accommodation accommodation) {
        this(
            accommodation.getId(),
            accommodation.getType(),
            accommodation.getName(),
            accommodation.getThumbnailUrl()
        );
    }
}
