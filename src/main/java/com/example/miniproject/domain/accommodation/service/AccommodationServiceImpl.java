package com.example.miniproject.domain.accommodation.service;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationRegisterRequest;
import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationPageResponse;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationRegisterResponse;
import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.AccommodationImage;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.global.exception.NoSuchEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Override
    @Transactional
    public AccommodationRegisterResponse register(AccommodationRegisterRequest request) {
        Accommodation accommodation = request.toEntity();
        request.images()
            .forEach(image -> accommodation.addImage(AccommodationImage.create(image)));
        return new AccommodationRegisterResponse(accommodationRepository.save(accommodation));
    }

    @Override
    @Transactional
    public AccommodationPageResponse search(AccommodationSearchCondition condition) {
        return new AccommodationPageResponse(
            accommodationRepository.findBySearchCondition(condition)
        );
    }

    @Override
    @Transactional
    public AccommodationDetailResponse getAccommodation(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
            .orElseThrow(NoSuchEntityException::new);
        return new AccommodationDetailResponse(accommodation);
    }
}
