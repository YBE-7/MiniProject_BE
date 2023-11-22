package com.example.miniproject.domain.accommodation.dto.response;

public record AccommodationResponse(
    Long id,
    String name,
    Double star,
    Integer price,
    String thumbnail
) {

}
