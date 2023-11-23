package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.order.dto.request.OrderRequest;
import com.example.miniproject.domain.order.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse.OrderDetail order(Long memberId, OrderRequest orderRequest);
}
