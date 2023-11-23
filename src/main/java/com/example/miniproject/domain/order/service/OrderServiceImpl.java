package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.cart.service.CartService;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.domain.order.dto.request.OrderItemRequest;
import com.example.miniproject.domain.order.dto.request.OrderRequest;
import com.example.miniproject.domain.order.dto.response.OrderResponse;
import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.order.repository.OrderRepository;
import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import com.example.miniproject.domain.roomtype.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final MemberRepository memberRepository;
    private final RoomTypeService roomTypeService;
    private final CartService cartService;

    @Override
    @Transactional
    public OrderResponse.OrderDetail order(Long memberId, OrderRequest orderRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Order order = orderRequest.toOrder(member);
        List<OrderItem> orderItems = new ArrayList<>();
        boolean success = false;

        for (OrderItemRequest orderItemRequest : orderRequest.orderItems()) {
            RoomType roomType = roomTypeRepository.findById(orderItemRequest.roomTypeId())
                .orElseThrow();

            Room room = roomTypeService.findAvailableRoom(
                roomType,
                orderItemRequest.checkinDate(),
                orderItemRequest.checkoutDate()
            ).orElseThrow();


            OrderItem orderItem = orderItemRequest.toOrderItem(room);
            order.addOrderItem(orderItem);
            orderItems.add(orderItem);
        }

        if (orderItems.size() == orderRequest.orderItems().size()) {
            orderRepository.save(order);
            cartService.deleteAllCartItem(memberId);
            success = true;
        }

        return OrderResponse.OrderDetail.fromEntity(order, orderItems, success);

    }
}
