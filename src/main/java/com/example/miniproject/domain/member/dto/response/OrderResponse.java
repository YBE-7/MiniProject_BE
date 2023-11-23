package com.example.miniproject.domain.member.dto.response;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
    LocalDate orderDate,
    List<OrderItemResponse> orderItems
) {

}
