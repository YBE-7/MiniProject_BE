package com.example.miniproject.domain.accommodation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record AccommodationsRegisterRequest(

    @NotEmpty
    List<@Valid AccommodationRegisterRequest> data
) {

}
