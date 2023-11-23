package com.example.miniproject.domain.order.dto.response;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    LocalDateTime orderTime,
    SubscriberResponse subscriber,
    ClientResponse client,
    ReservationInfoResponse reservationInfo,
    Integer totalPrice,
    List<OrderItemResponse> orderItems
) {
    public OrderResponse(Order order) {
        this(
            order.getCreatedAt(),
            new SubscriberResponse(order),
            new ClientResponse(order),
            new ReservationInfoResponse(order),
            order.getTotalPrice(),
            order.getOrderItems()
                .stream()
                .map(
                    orderItem -> {
                        RoomType roomType = orderItem.getRoom().getRoomType();
                        Accommodation accommodation = roomType.getAccommodation();
                        return new OrderItemResponse(orderItem, roomType, accommodation);
                    }
                )
                .toList()
        );
    }
}
