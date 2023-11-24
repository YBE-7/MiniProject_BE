package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.cart.repository.CartRepository;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.domain.order.dto.request.OrderItemRegisterRequest;
import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.response.OrderRegisterResponse;
import com.example.miniproject.domain.order.dto.response.OrderResponse;
import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.order.repository.OrderItemRepository;
import com.example.miniproject.domain.order.repository.OrderRepository;
import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import com.example.miniproject.domain.roomtype.service.RoomTypeService;
import com.example.miniproject.global.exception.AccessForbiddenException;
import com.example.miniproject.global.exception.NoSuchEntityException;
import com.example.miniproject.global.utils.CodeGenerator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final MemberRepository memberRepository;
    private final RoomTypeService roomTypeService;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public OrderRegisterResponse registerOrder(Long memberId, OrderRegisterRequest request) {
        Member member = memberRepository.getReferenceById(memberId);
        Order order = orderRepository.save(
            request.toEntity(member, CodeGenerator.generate())
        );
        request.orderItems()
            .forEach(orderItemRegisterRequest ->
                registerOrderItem(order, orderItemRegisterRequest)
            );
        cartRepository.deleteAllByMember(member);
        return new OrderRegisterResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse getOrder(Long memberId, Long orderId) {
        Member member = memberRepository.getReferenceById(memberId);
        Order order = orderRepository.findById(orderId)
            .orElseThrow(NoSuchEntityException::new);
        if (!Objects.equals(member, order.getMember())) {
            throw new AccessForbiddenException();
        }
        return new OrderResponse(order);
    }

    private void registerOrderItem(Order order, OrderItemRegisterRequest request) {
        Room room = findAvailableRoom(request);
        OrderItem orderItem = orderItemRepository.save(
            request.toEntity(
                order,
                room,
                CodeGenerator.generate()
            )
        );
        order.addOrderItem(orderItem);
    }

    private Room findAvailableRoom(OrderItemRegisterRequest request) {
        RoomType roomType = roomTypeRepository.findById(request.roomTypeId())
            .orElseThrow(NoSuchEntityException::new);
        return roomTypeService.findAvailableRoom(
            roomType,
            request.checkinDate(),
            request.checkoutDate()
        ).orElseThrow(RuntimeException::new);
    }
}
