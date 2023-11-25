package com.example.miniproject.domain.roomtype.service;

import static com.example.miniproject.global.constant.RoomTypeStatus.*;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypesRegisterRequest;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeDetailResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeRegisterResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.roomtype.dto.response.RoomTypesRegisterResponse;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.entity.RoomTypeImage;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import com.example.miniproject.global.constant.RoomTypeStatus;
import com.example.miniproject.global.exception.NoSuchEntityException;
import com.example.miniproject.global.utils.RandomNumberGenerator;
import com.example.miniproject.global.utils.ScheduleValidator;
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
    public RoomTypesRegisterResponse register(RoomTypesRegisterRequest request) {
        return new RoomTypesRegisterResponse(
            request.data()
            .stream()
            .map(this::register)
            .toList()
        );
    }

    @Override
    @Transactional
    public List<RoomTypeResponse> search(
        Long accommodationId,
        RoomTypeSearchCondition condition
    ) {
        ScheduleValidator.validate(condition.getFrom(), condition.getTo());

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
            .orElseThrow(NoSuchEntityException::new);

        return roomTypeRepository.findByAccommodation(accommodation)
            .stream()
            .map(roomType -> {
                Long stock = roomTypeRepository.getStockBySchedule(
                    roomType, condition.getFrom(), condition.getTo()
                );
                RoomTypeStatus status = getRoomTypeStatus(roomType, stock, condition.getCapacity());
                return new RoomTypeResponse(roomType, stock, status);
            })
            .sorted((r1, r2) -> {
                if (r1.status() == r2.status()) {
                    return Integer.compare(r1.price(), r2.price());
                }
                return Integer.compare(r2.status().getValue(), r1.status().getValue());
            })
            .toList();
    }

    @Override
    @Transactional
    public RoomTypeDetailResponse getRoomType(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
            .orElseThrow(NoSuchEntityException::new);
        return new RoomTypeDetailResponse(roomType);
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

    private RoomTypeRegisterResponse register(RoomTypeRegisterRequest request) {
        Accommodation accommodation = accommodationRepository.findById(request.accommodationId())
            .orElseThrow(NoSuchEntityException::new);
        RoomType roomType = roomTypeRepository.save(
            request.toEntity(accommodation)
        );
        request.images()
            .forEach(image -> roomType.addImage(RoomTypeImage.create(image)));

        int randomNumber = RandomNumberGenerator.generateNumberInRangeInclusive(1, 5);
        for (int index = 0; index < randomNumber; index++) {
            Room room = Room.create(roomType.getName() + index);
            roomType.addRoom(room);
        }

        return new RoomTypeRegisterResponse(roomType);
    }

    private RoomTypeStatus getRoomTypeStatus(
        RoomType roomType,
        Long stock,
        Integer capacity
    ) {
        if (stock == 0) {
            return NO_STOCK;
        }
        return (roomType.getCapacity() >= capacity) ? OK : OVER_CAPACITY;
    }
}
