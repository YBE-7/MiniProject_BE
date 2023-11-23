package com.example.miniproject.domain.cart.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.cart.entity.CartItem;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;

public record CartItemResponse(
    Long id,
    String accommodationName,
    String accommodationAddress,
    String accommodationImage,
    String roomTypeName,
    Integer price,
    Integer capacity,
    LocalDate checkinDate,
    LocalDate checkoutDate,
    Long stock
) {
    public CartItemResponse(
        CartItem cartItem,
        RoomType roomType,
        Accommodation accommodation,
        Long stock
    ) {
        this(
            cartItem.getId(),
            accommodation.getName(),
            accommodation.getLocation().getAddress(),
            accommodation.getThumbnailUrl(),
            roomType.getName(),
            roomType.getPrice(),
            roomType.getCapacity(),
            cartItem.getCheckinDate(),
            cartItem.getCheckoutDate(),
            stock
        );
    }
}
