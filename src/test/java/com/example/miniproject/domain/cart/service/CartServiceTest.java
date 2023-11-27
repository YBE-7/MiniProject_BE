package com.example.miniproject.domain.cart.service;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.cart.dto.request.CartItemRegisterRequest;
import com.example.miniproject.domain.cart.dto.response.CartItemRegisterResponse;
import com.example.miniproject.domain.cart.dto.response.CartItemResponse;
import com.example.miniproject.domain.cart.dto.response.CartResponse;
import com.example.miniproject.domain.cart.entity.CartItem;
import com.example.miniproject.domain.cart.repository.CartRepository;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.Role;
import com.example.miniproject.global.exception.NoStockException;
import com.example.miniproject.global.exception.NoSuchEntityException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartServiceImpl cartService;

    @Test
    void 장바구니_상품등록_성공테스트() {
        // Given

        // 회원등록
        Member testMember = Member.create(
            "test@test.com",
            "testName",
            "Testpassword5",
            Role.MEMBER
        );
        em.persist(testMember);

        // 숙소 등록
        Accommodation testAccommodation = Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "테스트 호텔",
            "테스트 호텔 소개",
            "테스트 호텔 서비스",
            5.0,
            Location.create("부산시 테스트동", 36.0, 128.0),
            "썸네일 테스트"
        );
        em.persist(testAccommodation);

        // 룸타입 등록
        RoomType testRoomType = RoomType.create(
            testAccommodation,
            "테스트 룸타입",
            555555,
            5,
            "테스트 룸타입 소개",
            "테스트 룸타입 서비스"
        );

        Room room1 = Room.create("testRoom1");
        Room room2 = Room.create("testRoom2");
        testRoomType.addRoom(room1);
        testRoomType.addRoom(room2);

        em.persist(testRoomType);

        CartItemRegisterRequest cartItemRegisterRequest =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 3)
            );

        // When
        CartItemRegisterResponse response = cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest);
        Optional<CartItem> registerCartItem = cartRepository.findById(response.cartItemId());

        // Then
        assertThat(registerCartItem.get().getMember().getId()).isEqualTo(testMember.getId());
    }

    @Test
    void 장바구니_상품등록_실패테스트() {
        // Given

        // 회원등록
        Member testMember = Member.create(
            "test@test.com",
            "testName",
            "Testpassword5",
            Role.MEMBER
        );
        em.persist(testMember);

        // 숙소 등록
        Accommodation testAccommodation = Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "테스트 호텔",
            "테스트 호텔 소개",
            "테스트 호텔 서비스",
            5.0,
            Location.create("부산시 테스트동", 36.0, 128.0),
            "썸네일 테스트"
        );
        em.persist(testAccommodation);

        // 룸타입 등록
        RoomType testRoomType = RoomType.create(
            testAccommodation,
            "테스트 룸타입",
            555555,
            5,
            "테스트 룸타입 소개",
            "테스트 룸타입 서비스"
        );

        Room room = Room.create("testRoom");
        testRoomType.addRoom(room);

        em.persist(testRoomType);

        CartItemRegisterRequest cartItemRegisterRequest =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 3)
            );

        // When/Then
        CartItemRegisterResponse response = cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest);
        Assertions.assertThrows(NoStockException.class, () ->
            cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest));
    }

    @Test
    void 장바구니_조회_테스트() {
        // Given

        // 회원등록
        Member testMember = Member.create(
            "test@test.com",
            "testName",
            "Testpassword5",
            Role.MEMBER
        );
        em.persist(testMember);

        // 숙소 등록
        Accommodation testAccommodation = Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "테스트 호텔",
            "테스트 호텔 소개",
            "테스트 호텔 서비스",
            5.0,
            Location.create("부산시 테스트동", 36.0, 128.0),
            "썸네일 테스트"
        );
        em.persist(testAccommodation);

        // 룸타입 등록
        RoomType testRoomType = RoomType.create(
            testAccommodation,
            "테스트 룸타입",
            1000,
            5,
            "테스트 룸타입 소개",
            "테스트 룸타입 서비스"
        );

        Room room1 = Room.create("testRoom1");
        Room room2 = Room.create("testRoom2");
        testRoomType.addRoom(room1);
        testRoomType.addRoom(room2);

        em.persist(testRoomType);

        CartItemRegisterRequest cartItemRegisterRequest1 =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 3)
            );
        CartItemRegisterRequest cartItemRegisterRequest2 =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 6),
                LocalDate.of(2023, 12, 9)
            );
        CartItemRegisterRequest cartItemRegisterRequest3 =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 10)
            );

        cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest1);
        cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest2);
        cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest3);

        // When
        CartResponse response = cartService.getCartItems(testMember.getId());

        // Then
        assertThat(response.cartItems().size()).isEqualTo(3);
        assertThat(response.totalPrice()).isEqualTo(3000);
    }

    @Test
    void 장바구니_상품삭제_테스트() {
        // Given

        // 회원등록
        Member testMember = Member.create(
            "test@test.com",
            "testName",
            "Testpassword5",
            Role.MEMBER
        );
        em.persist(testMember);

        // 숙소 등록
        Accommodation testAccommodation = Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "테스트 호텔",
            "테스트 호텔 소개",
            "테스트 호텔 서비스",
            5.0,
            Location.create("부산시 테스트동", 36.0, 128.0),
            "썸네일 테스트"
        );
        em.persist(testAccommodation);

        // 룸타입 등록
        RoomType testRoomType = RoomType.create(
            testAccommodation,
            "테스트 룸타입",
            1000,
            5,
            "테스트 룸타입 소개",
            "테스트 룸타입 서비스"
        );

        Room room1 = Room.create("testRoom1");
        Room room2 = Room.create("testRoom2");
        testRoomType.addRoom(room1);
        testRoomType.addRoom(room2);

        em.persist(testRoomType);

        CartItemRegisterRequest cartItemRegisterRequest =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 3)
            );

        cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest);

        // When/Then
        cartService.deleteCartItem(testMember.getId(), 1L);
        assertThrows(NoSuchEntityException.class, () ->
            cartService.deleteCartItem(testMember.getId(), 1L));
    }

    @Test
    void 장바구니_모든상품_삭제테스트() {
        // Given

        // 회원등록
        Member testMember = Member.create(
            "test@test.com",
            "testName",
            "Testpassword5",
            Role.MEMBER
        );
        em.persist(testMember);

        // 숙소 등록
        Accommodation testAccommodation = Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "테스트 호텔",
            "테스트 호텔 소개",
            "테스트 호텔 서비스",
            5.0,
            Location.create("부산시 테스트동", 36.0, 128.0),
            "썸네일 테스트"
        );
        em.persist(testAccommodation);

        // 룸타입 등록
        RoomType testRoomType = RoomType.create(
            testAccommodation,
            "테스트 룸타입",
            1000,
            5,
            "테스트 룸타입 소개",
            "테스트 룸타입 서비스"
        );

        Room room1 = Room.create("testRoom1");
        Room room2 = Room.create("testRoom2");
        testRoomType.addRoom(room1);
        testRoomType.addRoom(room2);

        em.persist(testRoomType);

        CartItemRegisterRequest cartItemRegisterRequest1 =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 3)
            );
        CartItemRegisterRequest cartItemRegisterRequest2 =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 6),
                LocalDate.of(2023, 12, 9)
            );

        cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest1);
        cartService.registerCartItem(testMember.getId(), cartItemRegisterRequest2);

        // When
        cartService.deleteAllCartItems(testMember.getId());
        assertThat(cartService.getCartItems(testMember.getId()).totalPrice()).isEqualTo(0);
        assertThat(cartService.getCartItems(testMember.getId()).cartItems().size()).isEqualTo(0);
    }

}
