package com.example.miniproject.domain.roomtype.service;

import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypesRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeDetailResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypesRegisterResponse;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomTypeService {
    RoomTypesRegisterResponse register(RoomTypesRegisterRequest request);

    List<RoomTypeResponse> getRoomTypes(
        Long accommodationId,
        RoomTypeSearchCondition condition
    );

    RoomTypeDetailResponse getRoomType(Long id);

    Optional<Room> findAvailableRoom(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    );
}
