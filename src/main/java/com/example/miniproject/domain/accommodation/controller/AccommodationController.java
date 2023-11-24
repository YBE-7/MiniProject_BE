package com.example.miniproject.domain.accommodation.controller;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.request.AccommodationsRegisterRequest;
import com.example.miniproject.domain.accommodation.service.AccommodationService;
import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.service.RoomTypeService;
import com.example.miniproject.global.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final RoomTypeService roomTypeService;

    @PostMapping
    public ResponseEntity<?> registerAccommodations(
        @RequestBody @Valid AccommodationsRegisterRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiUtils.success(
                    accommodationService.register(request)
                )
            );
    }

    @GetMapping
    public ResponseEntity<?> searchAccommodations(
        AccommodationSearchCondition condition
    ) {
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(
                    accommodationService.search(condition)
                )
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccommodation(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(
                    accommodationService.getAccommodation(id)
                )
            );
    }

    @GetMapping("/{id}/roomtypes")
    public ResponseEntity<?> searchRoomTypes(
        @PathVariable("id") Long id,
        @Valid RoomTypeSearchCondition condition
    ) {
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(
                    roomTypeService.search(id, condition)
                )
            );
    }
}
