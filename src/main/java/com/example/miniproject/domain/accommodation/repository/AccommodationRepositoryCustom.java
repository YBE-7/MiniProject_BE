package com.example.miniproject.domain.accommodation.repository;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationResponse;
import org.springframework.data.domain.Page;

public interface AccommodationRepositoryCustom {
    Page<AccommodationResponse> findBySearchCondition(AccommodationSearchCondition condition);
}
