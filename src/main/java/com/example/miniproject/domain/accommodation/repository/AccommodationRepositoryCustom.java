package com.example.miniproject.domain.accommodation.repository;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationResponse;
import java.util.List;

public interface AccommodationRepositoryCustom {
    List<AccommodationResponse> findBySearchCondition(AccommodationSearchCondition condition);
}
