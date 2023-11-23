package com.example.miniproject.domain.order.dto.response;


import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.order.entity.OrderItem;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderResponse {

    public record OrderDetail(
        @NotNull Integer totalPrice,
        @NotNull Integer totalCount,
        @NotNull List<RoomInfo> roomInfos,
        @NotNull MemberInfo memberInfo,
        @NotNull String paymentMethod,
        @NotNull Boolean success

    ) {
        public static OrderDetail fromEntity(Order order, List<OrderItem> orderItems, boolean success) {
            List<RoomInfo> roomList = new ArrayList<>();
            MemberInfo memberInfo = MemberInfo.fromEntity(order);

            for (OrderItem orderItem : orderItems) {
                RoomInfo roomInfo = RoomInfo.fromEntity(orderItem);
                roomList.add(roomInfo);
            }

            return new OrderDetail(order.getTotalPrice(), roomList.size(), roomList, memberInfo, order.getPaymentMethod(), success);
        }
    }

    public record MemberInfo(
        @NotNull String clientName,
        @NotNull String clientPhoneNumber,
        @NotNull String subscriberName,
        @NotNull String subscriberPhoneNumber
    ) {
        public static MemberInfo fromEntity(Order order) {
            return new MemberInfo(
                order.getClientName(),
                order.getClientPhoneNumber(),
                order.getSubscriberName(),
                order.getSubscriberPhoneNumber()
            );
        }
    }

    public record RoomInfo(
        @NotNull String roomName,
        @NotNull String roomTypeName,
        @NotNull LocalDate checkinDate,
        @NotNull LocalDate checkoutDate,
        @NotNull Integer capacity,
        @NotNull Integer price,
        @NotNull String image
    ) {
        public static RoomInfo fromEntity(OrderItem orderItem) {
            return new RoomInfo(
                orderItem.getRoom().getName(),
                orderItem.getRoom().getRoomType().getName(),
                orderItem.getCheckinDate(),
                orderItem.getCheckoutDate(),
                orderItem.getRoom().getRoomType().getCapacity(),
                orderItem.getRoom().getRoomType().getPrice(),
                orderItem.getRoom().getRoomType().getImages().get(0).getUrl()
            );
        }

    }
}



