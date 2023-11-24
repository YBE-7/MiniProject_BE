package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;

public record AccommodationResponse(
    Long id,
    String name,
    String image
) {
    public AccommodationResponse(Accommodation accommodation) {
        this(
            accommodation.getId(),
            accommodation.getName(),
            accommodation.getThumbnailUrl()
        );
    }
}
