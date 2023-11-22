package com.example.miniproject.domain.roomtype.service;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeRegisterResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.entity.RoomTypeImage;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import com.example.miniproject.global.exception.NoSuchEntityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final AccommodationRepository accommodationRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional
    public RoomTypeRegisterResponse register(RoomTypeRegisterRequest request) {
        Accommodation accommodation = accommodationRepository.findById(request.accommodationId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 엔티티"));
        RoomType roomType = request.toEntity(accommodation);
        request.images()
            .forEach(image -> roomType.addImage(RoomTypeImage.create(image)));
        return new RoomTypeRegisterResponse(roomTypeRepository.save(roomType));
    }

    @Override
    public List<RoomTypeResponse> getRoomTypes(
        Long accommodationId,
        RoomTypeSearchCondition condition
    ) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
            .orElseThrow(NoSuchEntityException::new);

        return roomTypeRepository
            .findByAccommodationAndCapacityGreaterThanEqual(accommodation, condition.capacity())
            .stream()
            .map(roomType -> {
                Long stock = roomTypeRepository.findStockBySearchCondition(roomType, condition);
                return new RoomTypeResponse(roomType, stock);
            })
            .toList();
    }
}
