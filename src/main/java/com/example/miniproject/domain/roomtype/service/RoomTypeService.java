package com.example.miniproject.domain.roomtype.service;

import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeRegisterResponse;

public interface RoomTypeService {
    RoomTypeRegisterResponse register(RoomTypeRegisterRequest request);
}
