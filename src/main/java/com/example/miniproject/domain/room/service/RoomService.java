package com.example.miniproject.domain.room.service;

import com.example.miniproject.domain.room.dto.request.RoomRegisterRequest;
import com.example.miniproject.domain.room.dto.response.RoomRegisterResponse;

public interface RoomService {

    RoomRegisterResponse register(RoomRegisterRequest request);
}
