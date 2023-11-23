package com.example.miniproject.domain.cart.dto.response;

import com.example.miniproject.domain.cart.entity.CartItem;

import java.time.LocalDate;

public record CartItemResponse(
    Long id,
    String accommodationName,
    String accommodationAddress,
    String roomTypeName,
    Integer price,
    Integer capacity,
    LocalDate checkinDate,
    LocalDate checkoutDate
) {
    public CartItemResponse(CartItem cartItem) {
        this(cartItem.getId(), cartItem.getRoomType().getAccommodation().getName(),
            cartItem.getRoomType().getAccommodation().getLocation().getAddress(),
            cartItem.getRoomType().getName(), cartItem.getRoomType().getPrice(),
            cartItem.getRoomType().getCapacity(), cartItem.getCheckinDate(),
            cartItem.getCheckoutDate());
    }
}
