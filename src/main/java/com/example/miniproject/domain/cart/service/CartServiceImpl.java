package com.example.miniproject.domain.cart.service;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.cart.dto.request.CartItemRegisterRequest;
import com.example.miniproject.domain.cart.dto.response.CartItemRegisterResponse;
import com.example.miniproject.domain.cart.dto.response.CartItemResponse;
import com.example.miniproject.domain.cart.dto.response.CartResponse;
import com.example.miniproject.domain.cart.entity.CartItem;
import com.example.miniproject.domain.cart.repository.CartRepository;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import com.example.miniproject.global.exception.AccessForbiddenException;
import com.example.miniproject.global.exception.NoSuchEntityException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional
    public CartResponse getCartItems(Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);
        List<CartItem> cartItems = cartRepository.findAllByMember(member);
        int totalPrice = cartItems.stream()
            .mapToInt(cartItem -> cartItem.getRoomType().getPrice())
            .sum();
        return new CartResponse(
            getCartItemResponses(cartItems),
            totalPrice
        );
    }

    @Override
    @Transactional
    public CartItemRegisterResponse registerCartItem(
        Long memberId,
        CartItemRegisterRequest cartItemRegisterRequest
    ) {
        Member member = memberRepository.getReferenceById(memberId);
        RoomType roomType = roomTypeRepository.findById(cartItemRegisterRequest.roomTypeId())
            .orElseThrow(NoSuchEntityException::new);
        Long roomTypeStock = roomTypeRepository.getStockBySchedule(
            roomType,
            cartItemRegisterRequest.checkinDate(),
            cartItemRegisterRequest.checkoutDate()
        );
        Long roomTypeStockInCart = cartRepository.countByMemberAndRoomTypeAndCheckinDateAndCheckoutDate(
            member,
            roomType,
            cartItemRegisterRequest.checkinDate(),
            cartItemRegisterRequest.checkoutDate()
        );

        if (roomTypeStock <= roomTypeStockInCart) {
            throw new RuntimeException(); // Todo 예외처리
        }

        CartItem cartItem = cartRepository.save(
            CartItem.create(
                member,
                roomType,
                cartItemRegisterRequest.checkinDate(),
                cartItemRegisterRequest.checkoutDate()
            )
        );

        return new CartItemRegisterResponse(cartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {
        Member member = memberRepository.getReferenceById(memberId);
        CartItem cartItem = cartRepository.findById(cartItemId)
            .orElseThrow(NoSuchEntityException::new);
        if (!Objects.equals(member, cartItem.getMember())) {
            throw new AccessForbiddenException();
        }
        cartRepository.deleteById(cartItemId);
    }

    @Override
    @Transactional
    public void deleteAllCartItems(Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);
        cartRepository.deleteAllByMember(member);
    }

    private List<CartItemResponse> getCartItemResponses(List<CartItem> cartItems) {
        return cartItems.stream()
            .map(cartItem -> {
                RoomType roomType = cartItem.getRoomType();
                Accommodation accommodation = roomType.getAccommodation();
                Long stock = roomTypeRepository.getStockBySchedule(
                    roomType,
                    cartItem.getCheckinDate(),
                    cartItem.getCheckoutDate()
                );
                return new CartItemResponse(
                    cartItem,
                    roomType,
                    accommodation,
                    stock
                );
            })
            .toList();
    }
}
