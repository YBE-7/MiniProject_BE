package com.example.miniproject.domain.roomtype.service;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeRegisterResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.entity.RoomTypeImage;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import com.example.miniproject.global.exception.NoSuchEntityException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    @Transactional
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
                Long stock = getStock(
                    roomType,
                    condition.checkinDate(),
                    condition.checkoutDate()
                );
                return new RoomTypeResponse(roomType, stock);
            })
            .toList();
    }

    @Override
    @Transactional
    public Long getStock(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        return roomTypeRepository.findStockBySchedule(
            roomType,
            checkinDate,
            checkoutDate
        );
    }

    @Override
    @Transactional
    public Optional<Room> findAvailableRoom(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        return roomTypeRepository
            .findAvailableRooms(
                roomType,
                checkinDate,
                checkoutDate
            )
            .stream()
            .findAny();
    }
}
