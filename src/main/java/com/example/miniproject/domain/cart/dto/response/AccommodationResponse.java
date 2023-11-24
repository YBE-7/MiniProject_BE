package com.example.miniproject.domain.cart.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;

public record AccommodationResponse(
    Long id,
    String name,
    String address,
    String image
) {
    public AccommodationResponse(Accommodation accommodation) {
        this(
            accommodation.getId(),
            accommodation.getName(),
            accommodation.getLocation().getAddress(),
            accommodation.getThumbnailUrl()
        );
    }
}
