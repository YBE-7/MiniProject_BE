package com.example.miniproject.domain.order.controller;

import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.service.OrderService;
import com.example.miniproject.global.security.MemberDetails;
import com.example.miniproject.global.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> order(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @RequestBody @Valid OrderRegisterRequest orderRegisterRequest
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiUtils.success(
                    orderService.order(memberDetails.getId(), orderRegisterRequest)
                )
            );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(
        @AuthenticationPrincipal MemberDetails memberDetails,
        @PathVariable("id") Long orderId
    ) {
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(
                    orderService.getOrder(memberDetails.getId(), orderId)
                )
            );
    }
}
