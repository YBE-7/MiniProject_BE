package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.response.OrderRegisterResponse;
import com.example.miniproject.domain.order.dto.response.OrderResponse;

public interface OrderService {
    OrderRegisterResponse registerOrder(Long memberId, OrderRegisterRequest request);

    OrderResponse getOrder(Long memberId, Long orderId);
}
