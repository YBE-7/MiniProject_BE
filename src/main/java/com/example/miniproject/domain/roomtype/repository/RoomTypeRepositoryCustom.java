package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;

public interface RoomTypeRepositoryCustom {
    Long findStockBySchedule(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    );
}
