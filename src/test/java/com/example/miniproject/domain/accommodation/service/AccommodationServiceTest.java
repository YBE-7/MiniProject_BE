package com.example.miniproject.domain.accommodation.service;

import static com.example.miniproject.global.constant.AccommodationType.*;
import static com.example.miniproject.global.constant.Region.*;
import static org.assertj.core.api.Assertions.*;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationRegisterRequest;
import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.request.AccommodationsRegisterRequest;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationPageResponse;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationsRegisterResponse;
import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypesRegisterRequest;
import com.example.miniproject.domain.roomtype.service.RoomTypeService;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.SearchOrderCondition;
import com.example.miniproject.global.exception.InvalidParamException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AccommodationServiceTest {

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomTypeService roomTypeService;

    @DisplayName("정상적인 숙소 등록 요청일 경우, 정상적으로 저장됩니다.")
    @Test
    void register_success() {
        //given
        AccommodationsRegisterRequest request = new AccommodationsRegisterRequest(
            List.of(
                singleAccommodationRegisterRequest(
                    HOTEL, SEOUL, 4.7
                ),
                singleAccommodationRegisterRequest(
                    HOTEL, BUSAN, 5.0
                ),
                singleAccommodationRegisterRequest(
                    POOL_VILLA, SEOUL, 4.5
                )
            )
        );

        //when
        AccommodationsRegisterResponse response = accommodationService.register(request);

        //then
        assertThat(response.ids().size()).isEqualTo(3);
    }

    @DisplayName("정상적인 숙소 조회 요청일 경우, 정상적으로 조회됩니다.")
    @Test
    void get_success() {
        //given
        Accommodation accommodation = accommodationRepository.save(Accommodation.create(
            HOTEL,
            SEOUL,
            "name",
            "introduction",
            "service",
            4.0,
            Location.create(
                "address",
                123.123,
                123.123
            ),
            "thumbnail"
        ));

        //when
        AccommodationDetailResponse response = accommodationService.getAccommodation(
            accommodation.getId()
        );

        //then
        assertThat(response.id()).isEqualTo(accommodation.getId());
    }

    @DisplayName("정상적인 숙소 검색 요청일 경우, 정상적으로 검색됩니다.")
    @Test
    void search_success_by_type_and_order_by_star() {
        //given
        AccommodationsRegisterResponse accommodationsRegisterResponse = accommodationService.register(
            new AccommodationsRegisterRequest(
                List.of(
                    singleAccommodationRegisterRequest(
                        HOTEL, SEOUL, 4.7
                    ),
                    singleAccommodationRegisterRequest(
                        HOTEL, BUSAN, 5.0
                    ),
                    singleAccommodationRegisterRequest(
                        POOL_VILLA, SEOUL, 4.5
                    )
                )
            )
        );
        accommodationsRegisterResponse.ids()
            .forEach(accommodationRegisterResponse -> roomTypeService.register(
                    new RoomTypesRegisterRequest(
                        List.of(
                           singleRoomTypeRegisterRequest(accommodationRegisterResponse.accommodationId())
                        )
                    )
                )
            );

        //when
        AccommodationPageResponse response = accommodationService.search(
            new AccommodationSearchCondition(
                HOTEL,
                null, null, null, null,
                SearchOrderCondition.STAR_DESC,
                null, null
            )
        );

        //then
        assertThat(response.accommodations().size()).isEqualTo(2);
        assertThat(response.accommodations().get(0).id())
            .isEqualTo(accommodationsRegisterResponse.ids().get(1).accommodationId());
        assertThat(response.accommodations().get(1).id())
            .isEqualTo(accommodationsRegisterResponse.ids().get(0).accommodationId());
    }

    @DisplayName("유효하지 않은 날짜의 숙소 검색 요청일 경우, 예외가 반환됩니다.")
    @Test
    void search_fail_schedule() {
        //given
        AccommodationsRegisterResponse accommodationsRegisterResponse = accommodationService.register(
            new AccommodationsRegisterRequest(
                List.of(
                    singleAccommodationRegisterRequest(
                        HOTEL, SEOUL, 4.7
                    ),
                    singleAccommodationRegisterRequest(
                        HOTEL, BUSAN, 5.0
                    ),
                    singleAccommodationRegisterRequest(
                        POOL_VILLA, SEOUL, 4.5
                    )
                )
            )
        );
        accommodationsRegisterResponse.ids()
            .forEach(accommodationRegisterResponse -> roomTypeService.register(
                    new RoomTypesRegisterRequest(
                        List.of(
                            singleRoomTypeRegisterRequest(accommodationRegisterResponse.accommodationId())
                        )
                    )
                )
            );

        //when & then
        assertThatThrownBy(() -> accommodationService.search(
            new AccommodationSearchCondition(
                HOTEL,
                null,
                LocalDate.of(2023, 11, 28),
                LocalDate.of(2023, 11, 26),
                null,
                SearchOrderCondition.STAR_DESC,
                null, null
            )
        )).isInstanceOf(InvalidParamException.class);
    }

    private AccommodationRegisterRequest singleAccommodationRegisterRequest(
        AccommodationType type,
        Region region,
        Double star
    ) {
        return new AccommodationRegisterRequest(
            type,
            region,
            "name",
            "introduction",
            "service",
            star,
            "image",
            "address",
            123.123,
            123.123,
            List.of()
        );
    }

    private RoomTypeRegisterRequest singleRoomTypeRegisterRequest(
        Long accommodationId
    ) {
        return new RoomTypeRegisterRequest(
            accommodationId,
            "name",
            10000,
            5,
            "introduction",
            "service",
            List.of()
        );
    }
}
