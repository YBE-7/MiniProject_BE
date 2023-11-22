package com.example.miniproject.domain.roomtype.service;

import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeRegisterResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import java.util.List;

public interface RoomTypeService {
    RoomTypeRegisterResponse register(RoomTypeRegisterRequest request);

    List<RoomTypeResponse> getRoomTypes(Long accommodationId, RoomTypeSearchCondition condition);
}
