package com.example.miniproject.domain.cart.controller;

import com.example.miniproject.domain.cart.dto.request.CartItemRequest;
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
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> showCartItems(
        @AuthenticationPrincipal MemberDetails memberDetails
        ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiUtils.success(
                    cartService.showCartItems(memberDetails.getId())
                )
            );
    }

    @PostMapping
    public ResponseEntity<?> addCartItem(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @RequestBody @Valid CartItemRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiUtils.success(
                    cartService.addCartItem(memberDetails.getId(), request)
                )
            );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @PathVariable Long cartItemId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiUtils.success(
                cartService.deleteCartItem(memberDetails.getId(), cartItemId)
            ));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllCartItem(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @RequestBody CartItemRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiUtils.success(
                cartService.deleteAllCartItem(memberDetails.getId())
            ));
    }
}
