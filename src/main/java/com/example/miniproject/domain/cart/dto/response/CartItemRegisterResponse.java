package com.example.miniproject.domain.cart.dto.response;

import com.example.miniproject.domain.cart.entity.CartItem;

public record CartItemRegisterResponse(
    Long cartItemId
) {
    public CartItemRegisterResponse(CartItem cartItem) {
        this(cartItem.getId());
    }
}
