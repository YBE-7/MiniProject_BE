package com.example.miniproject.domain.cart.service;

import com.example.miniproject.domain.cart.dto.request.CartItemRequest;
import com.example.miniproject.domain.cart.dto.response.CartItemResponse;

import java.util.List;

public interface CartService {

    List<CartItemResponse> showCartItems(Long memberId);

    Long addCartItem(Long memberId, CartItemRequest cartItemRequest);

    Long deleteCartItem(Long memberId, Long cartItemId);

    Long deleteAllCartItem(Long memberId);
}
