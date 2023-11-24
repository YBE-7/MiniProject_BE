package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;
import java.util.List;

public interface RoomTypeRepositoryCustom {
    List<RoomTypeResponse> findBySearchCondition(
        Accommodation accommodation,
        RoomTypeSearchCondition condition
    );

    Long getStockBySchedule(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    );

    List<Room> findAvailableRooms(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    );
}
