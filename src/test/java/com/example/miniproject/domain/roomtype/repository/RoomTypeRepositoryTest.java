package com.example.miniproject.domain.roomtype.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(QueryDslTestConfig.class)
class RoomTypeRepositoryTest {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @DisplayName("해당 조건에 방이 있을 경우, 방 갯수를 반환합니다.")
    @Test
    void getStockBySchedule() {
        //given
        Accommodation accommodation = accommodationRepository.save(
            singleAccommodation()
        );
        RoomType roomType = roomTypeRepository.save(
            singleRoomType(accommodation)
        );
        roomRepository.save(
            singleRoom(roomType)
        );
        roomRepository.save(
            singleRoom(roomType)
        );

        //when
        Long stock = roomTypeRepository.getStockBySchedule(
            roomType,
            LocalDate.of(2023, 11, 28),
            LocalDate.of(2023, 12, 1)
        );

        //then
        assertThat(stock).isEqualTo(2L);
    }

    @DisplayName("해당 조건에 방이 있을 경우, 방들의 리스트를 반환합니다.")
    @Test
    void findAvailableRooms() {
        //given
        Accommodation accommodation = accommodationRepository.save(
            singleAccommodation()
        );
        RoomType roomType = roomTypeRepository.save(
            singleRoomType(accommodation)
        );
        Room room1 = roomRepository.save(
            singleRoom(roomType)
        );
        Room room2 = roomRepository.save(
            singleRoom(roomType)
        );

        //when
        List<Room> rooms = roomTypeRepository.findAvailableRooms(
            roomType,
            LocalDate.of(2023, 11, 28),
            LocalDate.of(2023, 12, 1)
        );

        //then
        assertThat(rooms.size()).isEqualTo(2);
        assertThat(rooms).containsExactly(room1, room2);
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

    private RoomType singleRoomType(Accommodation accommodation) {
        return RoomType.create(
            accommodation,
            "name",
            10000,
            5,
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