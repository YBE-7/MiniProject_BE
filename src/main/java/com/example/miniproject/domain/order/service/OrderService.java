package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.response.OrderRegisterResponse;
import com.example.miniproject.domain.order.dto.response.OrderResponse;

public interface OrderService {
    //OrderResponse.OrderDetail order(Long memberId, OrderRegisterRequest orderRegisterRequest);

    OrderRegisterResponse order(Long memberId, OrderRegisterRequest request);

    OrderResponse getOrder(Long memberId, Long orderId);
}
