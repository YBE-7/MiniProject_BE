package com.example.miniproject.domain.cart.dto.response;

import java.util.List;

public record CartResponse(
    List<CartItemResponse> cartItems
) {

}
