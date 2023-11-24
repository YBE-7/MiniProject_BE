package com.example.miniproject.domain.accommodation.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record AccommodationPageResponse(
    List<AccommodationResponse> accommodations,
    long totalElements,
    int totalPages
) {
    public AccommodationPageResponse(
        Page<AccommodationResponse> page
    ) {
        this(
            page.getContent(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
}
