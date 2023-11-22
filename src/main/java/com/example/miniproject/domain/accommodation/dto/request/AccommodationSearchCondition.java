package com.example.miniproject.domain.accommodation.dto.request;

import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.SearchOrderCondition;
import java.time.LocalDate;

public record AccommodationSearchCondition(
    AccommodationType type,
    Region region,
    LocalDate checkinDate,
    LocalDate checkoutDate,
    Integer capacity,
    SearchOrderCondition order,
    Integer page,
    Integer size
) {

}
