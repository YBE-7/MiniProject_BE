package com.example.miniproject.domain.cart.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.cart.entity.CartItem;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;

public record CartItemResponse(
    Long id,
    AccommodationResponse accommodation,
    RoomTypeResponse roomType,
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
            new AccommodationResponse(accommodation),
            new RoomTypeResponse(roomType),
            cartItem.getCheckinDate(),
            cartItem.getCheckoutDate(),
            stock
        );
    }
}
