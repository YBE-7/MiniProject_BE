package com.example.miniproject.domain.roomtype.service;

import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeRegisterResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomTypeService {
    RoomTypeRegisterResponse register(RoomTypeRegisterRequest request);

    List<RoomTypeResponse> getRoomTypes(Long accommodationId, RoomTypeSearchCondition condition);

    Long getStock(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    );

    Optional<Room> findAvailableRoom(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    );
}
