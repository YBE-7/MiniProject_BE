package com.example.miniproject.domain.accommodation.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;

public record AccommodationRegisterResponse(
    Long accommodationId
) {
    public AccommodationRegisterResponse(Accommodation accommodation) {
        this(accommodation.getId());
    }
}
