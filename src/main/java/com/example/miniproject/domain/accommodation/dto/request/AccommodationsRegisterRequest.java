package com.example.miniproject.domain.accommodation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AccommodationsRegisterRequest(

    @NotNull
    @Valid
    List<AccommodationRegisterRequest> data
) {

}
