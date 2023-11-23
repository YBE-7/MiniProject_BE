package com.example.miniproject.domain.cart.service;

import com.example.miniproject.domain.cart.dto.request.CartItemRegisterRequest;
import com.example.miniproject.domain.cart.dto.response.CartItemRegisterResponse;
import com.example.miniproject.domain.cart.dto.response.CartResponse;

public interface CartService {

    CartResponse getCartItems(Long memberId);

    CartItemRegisterResponse registerCartItem(Long memberId, CartItemRegisterRequest cartItemRegisterRequest);

    void deleteCartItem(Long memberId, Long cartItemId);

    void deleteAllCartItems(Long memberId);
}
