package com.example.miniproject.domain.accommodation.dto.response;

import com.example.miniproject.domain.accommodation.entity.Location;

public record LocationResponse(
    String address,
    Double latitude,
    Double longitude
) {
    public LocationResponse(Location location) {
        this(
            location.getAddress(),
            location.getLatitude(),
            location.getLongitude()
        );
    }
}
