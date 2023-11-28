package com.example.miniproject.domain.roomtype.service;

import static org.assertj.core.api.Assertions.*;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.RoomRepository;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RoomTypeServiceTest {

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @DisplayName("객실 타입을 조회했을 경우, 객실이 있고 해당 인원 수보다 크며 가격이 낮은 순으로 반환합니다.")
    @Test
    void search() {
        //given
        Accommodation accommodation = accommodationRepository.save(singleAccommodation());
        RoomType roomType1 = roomTypeRepository.save(
            singleRoomType(accommodation, 1000, 4)
        );
        RoomType roomType2 = roomTypeRepository.save(
            singleRoomType(accommodation, 1500, 3)
        );
        RoomType roomType3 = roomTypeRepository.save(
            singleRoomType(accommodation, 700, 7)
        );
        roomRepository.save(singleRoom(roomType1));
        roomRepository.save(singleRoom(roomType2));
        roomRepository.save(singleRoom(roomType3));

        //when
        RoomTypeSearchCondition condition = new RoomTypeSearchCondition();
        condition.setFrom(LocalDate.of(2023, 12, 1));
        condition.setTo(LocalDate.of(2023, 12, 3));
        condition.setCapacity(4);
        List<RoomTypeResponse> response = roomTypeService.search(
            accommodation.getId(),
            condition
        );

        //then
        assertThat(response.size()).isEqualTo(3);
        assertThat(response.get(0).id()).isEqualTo(roomType3.getId());
        assertThat(response.get(1).id()).isEqualTo(roomType1.getId());
        assertThat(response.get(2).id()).isEqualTo(roomType2.getId());
    }

    private Accommodation singleAccommodation() {
        return Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "킹호텔",
            "제일 좋습니다",
            "조식,와이파이",
            4.7,
            Location.create(
                "서울시 강남구",
                122.124125,
                123.1235426
            ),
            "thumbnailUrl"
        );
    }

    private RoomType singleRoomType(
        Accommodation accommodation,
        int price,
        int capacity
    ) {
        return RoomType.create(
            accommodation,
            "name",
            price,
            capacity,
            "introduction",
            "service"
        );
    }

    private Room singleRoom(RoomType roomType) {
        return Room.create(
            roomType,
            "room1"
        );
    }
}