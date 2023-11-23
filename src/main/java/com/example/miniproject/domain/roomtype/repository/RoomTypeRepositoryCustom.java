package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;
import java.util.List;

public interface RoomTypeRepositoryCustom {
    Long findStockBySchedule(
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
