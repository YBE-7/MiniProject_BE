package com.example.miniproject.domain.cart.controller;

import com.example.miniproject.domain.cart.dto.request.CartItemRegisterRequest;
import com.example.miniproject.domain.cart.service.CartService;
import com.example.miniproject.global.security.MemberDetails;
import com.example.miniproject.global.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCartItems(
        @AuthenticationPrincipal MemberDetails memberDetails
        ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiUtils.success(
                    cartService.getCartItems(memberDetails.getId())
                )
            );
    }

    @PostMapping
    public ResponseEntity<?> registerCartItem(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @RequestBody @Valid CartItemRegisterRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiUtils.success(
                    cartService.registerCartItem(memberDetails.getId(), request)
                )
            );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(memberDetails.getId(), cartItemId);
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(null)
            );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllCartItem(
        @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        cartService.deleteAllCartItems(memberDetails.getId());
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(null)
            );
    }
}
