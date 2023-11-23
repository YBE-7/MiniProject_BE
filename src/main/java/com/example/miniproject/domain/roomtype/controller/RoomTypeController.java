package com.example.miniproject.domain.roomtype.controller;

import com.example.miniproject.domain.roomtype.dto.request.RoomTypeRegisterRequest;
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
@RequestMapping("/api/roomtypes")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping
    public ResponseEntity<?> registerRoomType(
        @RequestBody @Valid RoomTypeRegisterRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiUtils.success(
                    roomTypeService.register(request)
                )
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomType(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(
                    roomTypeService.getRoomType(id)
                )
            );
    }
}
