package com.example.miniproject.domain.room.service;

import com.example.miniproject.domain.room.dto.request.RoomRegisterRequest;
import com.example.miniproject.domain.room.dto.response.RoomRegisterResponse;
import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.room.repository.RoomRepository;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional
    public RoomRegisterResponse register(RoomRegisterRequest request) {
        RoomType roomType = roomTypeRepository.findById(request.roomTypeId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 엔티티"));
        Room room = request.toEntity(roomType);
        return new RoomRegisterResponse(roomRepository.save(room));
    }
}
