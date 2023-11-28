package com.example.miniproject.global.utils;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceCalculator {

    public static int calculateRoomTypePrice(
        RoomType roomType,
        LocalDate from,
        LocalDate to
    ) {
        return roomType.getPrice() * DaysCalculator.calculateDifference(from, to);
    }
}
