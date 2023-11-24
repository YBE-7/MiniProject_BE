package com.example.miniproject.domain.member.dto.response;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
    Long id,
    LocalDate orderDate,
    List<OrderItemResponse> orderItems
) {

}
