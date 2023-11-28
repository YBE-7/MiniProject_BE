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
import com.example.miniproject.global.exception.NoStockException;
import com.example.miniproject.global.exception.NoSuchEntityException;
import com.example.miniproject.global.utils.ScheduleValidator;
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
        return new CartResponse(
            getCartItemResponses(cartItems)
        );
    }

    @Override
    @Transactional
    public CartItemRegisterResponse registerCartItem(
        Long memberId,
        CartItemRegisterRequest request
    ) {
        ScheduleValidator.validate(request.checkinDate(), request.checkoutDate());

        Member member = memberRepository.getReferenceById(memberId);
        RoomType roomType = roomTypeRepository.findById(request.roomTypeId())
            .orElseThrow(NoSuchEntityException::new);

        validateCartItemRegisterRequest(request, member, roomType);

        CartItem cartItem = cartRepository.save(
            CartItem.create(
                member,
                roomType,
                request.checkinDate(),
                request.checkoutDate()
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
            .map(this::getCartItemResponse)
            .toList();
    }

    private CartItemResponse getCartItemResponse(CartItem cartItem) {
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
    }

    private void validateCartItemRegisterRequest(
        CartItemRegisterRequest request,
        Member member,
        RoomType roomType
    ) {
        Long roomTypeStock = roomTypeRepository.getStockBySchedule(
            roomType,
            request.checkinDate(),
            request.checkoutDate()
        );
        Long roomTypeStockInCart = cartRepository.countByMemberAndRoomTypeAndCheckinDateAndCheckoutDate(
            member,
            roomType,
            request.checkinDate(),
            request.checkoutDate()
        );

        if (roomTypeStock <= roomTypeStockInCart) {
            throw new NoStockException();
        }
    }
}
