package com.example.miniproject.domain.cart.service;

import com.example.miniproject.domain.cart.dto.request.CartItemRequest;
import com.example.miniproject.domain.cart.dto.response.CartItemResponse;
import com.example.miniproject.domain.cart.entity.CartItem;
import com.example.miniproject.domain.cart.repository.CartRepository;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final RoomTypeRepository roomTypeRepository;

    public List<CartItemResponse> showCartItems(Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);

        return cartRepository.findAllByMember(member).stream()
            .map(CartItemResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public Long addCartItem(Long memberId, CartItemRequest cartItemRequest) {
        Member member = memberRepository.getReferenceById(memberId);
        RoomType roomType = roomTypeRepository.getReferenceById(cartItemRequest.roomTypeId());
        Long roomTypeStock = roomTypeRepository.getStockBySchedule(
            roomType,
            cartItemRequest.checkinDate(),
            cartItemRequest.checkoutDate()
        );
        Long roomTypeStockInCart = cartRepository.countByMemberAndRoomType(member, roomType);

        if(roomTypeStock - roomTypeStockInCart == 0) {
            throw new RuntimeException(); // Todo 예외처리
        }

        CartItem newCartItem = CartItem.create(
            memberRepository.getReferenceById(memberId),
            roomTypeRepository.getReferenceById(cartItemRequest.roomTypeId()),
            cartItemRequest.checkinDate(),
            cartItemRequest.checkoutDate());

        cartRepository.save(newCartItem);

        return newCartItem.getId();
    }

    @Transactional
    public Long deleteCartItem(Long memberId, Long cartItemId) {
        Member member = memberRepository.getReferenceById(memberId);

        cartRepository.deleteByMemberAndId(member, cartItemId);

        return cartItemId;
    }

    @Transactional
    public Long deleteAllCartItem(Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);

        cartRepository.deleteAllByMember(member);

        return memberId;
    }
}
