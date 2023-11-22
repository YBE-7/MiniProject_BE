package com.example.miniproject.domain.accommodation.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationRegisterRequest;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationRegisterResponse;
import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceRegisterTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    @Test
    void register_accommodation_success() {
        //given
        AccommodationRegisterRequest request = new AccommodationRegisterRequest(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "킹호텔",
            "좋습니다",
            "호텔,조식",
            4.7,
            "thumbnail",
            "서울시 강남구",
            127.12312314,
            123.1231425,
            List.of("img1", "img2")
        );
        given(accommodationRepository.save(any())).willReturn(singleAccommodation());

        //when
        AccommodationRegisterResponse response = accommodationService.register(request);

        //then
        assertThat(response).isNotNull();
    }

    private Accommodation singleAccommodation() {
        return Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "킹호텔",
            "좋습니다",
            "호텔,조식",
            4.7,
            Location.create(
                "서울시 강남구",
                127.12312314,
                123.1231425
            ),
            "thumbnail"
        );
    }
}
