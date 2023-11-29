package com.example.miniproject.domain.cart.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.cart.entity.CartItem;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.global.utils.PriceCalculator;
import java.time.LocalDate;

public record CartItemResponse(
    Long id,
    AccommodationResponse accommodation,
    RoomTypeResponse roomType,
    LocalDate checkinDate,
    LocalDate checkoutDate,
    Long stock,
    Integer price
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
            stock,
            PriceCalculator.calculateRoomTypePrice(
                roomType,
                cartItem.getCheckinDate(),
                cartItem.getCheckoutDate()
            )
        );
    }
}
