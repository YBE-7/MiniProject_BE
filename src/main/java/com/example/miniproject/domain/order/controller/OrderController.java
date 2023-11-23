package com.example.miniproject.domain.order.controller;

import com.example.miniproject.domain.order.dto.request.OrderRequest;
import com.example.miniproject.domain.order.service.OrderService;
import com.example.miniproject.global.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> order(
        @RequestBody @Valid OrderRequest orderRequest,
        @AuthenticationPrincipal MemberDetails memberDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.order(memberDetails.getId(), orderRequest));
    }
}
