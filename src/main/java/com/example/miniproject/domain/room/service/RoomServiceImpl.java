package com.example.miniproject.domain.room.service;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.room.dto.request.RoomRegisterRequest;
import com.example.miniproject.domain.room.dto.response.RoomRegisterResponse;
import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.room.entity.RoomImage;
import com.example.miniproject.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    @Transactional
    public RoomRegisterResponse register(RoomRegisterRequest request) {
        Accommodation accommodation = accommodationRepository.findById(request.accommodationId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 엔티티"));
        Room room = request.toEntity(accommodation);
        request.images()
            .stream()
            .forEach(image -> room.addImage(RoomImage.create(image)));
        return new RoomRegisterResponse(roomRepository.save(room));
    }
}
