package com.example.miniproject.domain.accommodation.service;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.request.AccommodationsRegisterRequest;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationPageResponse;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationsRegisterResponse;

public interface AccommodationService {

    AccommodationsRegisterResponse register(AccommodationsRegisterRequest request);

    AccommodationPageResponse search(AccommodationSearchCondition condition);

    AccommodationDetailResponse getAccommodation(Long id);
}
