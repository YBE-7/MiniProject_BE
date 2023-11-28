package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.order.dto.request.ClientRequest;
import com.example.miniproject.domain.order.dto.request.OrderItemRegisterRequest;
import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.request.SubscriberRequest;
import com.example.miniproject.domain.order.dto.response.OrderResponse;
import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.order.repository.OrderRepository;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.create(
            "test@naver.com",
            "홍길동",
            "123456",
            Role.MEMBER);
        em.persist(member);

        Member member2 = Member.create(
            "test2@naver.com",
            "test2",
            "123456",
            Role.MEMBER);
        em.persist(member2);

        Accommodation accommodation = Accommodation.create(
            AccommodationType.HOTEL,
            Region.SEOUL,
            "호텔A",
            "안녕하세요",
            "호텔,조식",
            4.7,
            Location.create("서울시 강남구", 117.123, 127.456),
            "thumbnailUrl"
        );
        em.persist(accommodation);

        RoomType roomType1 = RoomType.create(
            accommodation,
            "StandardA",
            50000,
            4,
            "좋아요",
            "좋아요"
        );
        em.persist(roomType1);

        Room room1 = Room.create("101호");
        Room room2 = Room.create("102호");
        room1.setRoomType(roomType1);
        room2.setRoomType(roomType1);
        em.persist(room1);
        em.persist(room2);

        RoomType roomType2 = RoomType.create(
            accommodation,
            "StandardB",
            60000,
            4,
            "좋아요",
            "좋아요"
        );
        em.persist(roomType2);

        Room room3 = Room.create("103호");
        Room room4 = Room.create("104호");
        room3.setRoomType(roomType2);
        room4.setRoomType(roomType2);
        em.persist(room3);
        em.persist(room4);

        em.flush();
        em.clear();
    }

    @Test
    void NoStock() {
        //given
        OrderRegisterRequest request = new OrderRegisterRequest(
            Arrays.asList(
                new OrderItemRegisterRequest(1L, LocalDate.parse("2023-11-29"), LocalDate.parse("2023-11-30")),
                new OrderItemRegisterRequest(1L, LocalDate.parse("2023-11-29"), LocalDate.parse("2023-11-30")),
                new OrderItemRegisterRequest(1L, LocalDate.parse("2023-11-29"), LocalDate.parse("2023-11-30"))
            ),
            new ClientRequest("홍길동", "010-1234-5678"),
            new SubscriberRequest("홍길동", "010-1234-5678"),
            "card"
        );


        //when, then
        Assertions.assertThrows(RuntimeException.class, () -> orderService.registerOrder(1L, request));
    }

    @Test
    void NoRoomType() {
        //given
        OrderRegisterRequest request = new OrderRegisterRequest(
            List.of(
                new OrderItemRegisterRequest(3L, LocalDate.parse("2023-11-29"), LocalDate.parse("2023-11-30"))
            ),
            new ClientRequest("홍길동", "010-1234-5678"),
            new SubscriberRequest("홍길동", "010-1234-5678"),
            "card"
        );

        //when, then
        Assertions.assertThrows(RuntimeException.class, () -> orderService.registerOrder(1L, request));
    }

    @Test
    void registerOrder() {
        //given
        OrderRegisterRequest request = new OrderRegisterRequest(
            Arrays.asList(
                new OrderItemRegisterRequest(1L, LocalDate.parse("2023-11-27"), LocalDate.parse("2023-11-28")),
                new OrderItemRegisterRequest(2L, LocalDate.parse("2023-11-29"), LocalDate.parse("2023-11-30"))
            ),
            new ClientRequest("홍길동", "010-1234-5678"),
            new SubscriberRequest("홍길동", "010-1234-5678"),
            "card"
        );


        //when

        orderService.registerOrder(1L, request);
        Optional<Order> order = orderRepository.findById(1L);
        OrderResponse orderResponse = orderService.getOrder(1L, 1L);

        //then
        order.ifPresent(value -> Assertions.assertEquals(value.getOrderItems().size(), 2));

        Assertions.assertEquals(orderResponse.client().name(), "홍길동");
        Assertions.assertEquals(orderResponse.client().phoneNumber(), "010-1234-5678");
        Assertions.assertEquals(orderResponse.subscriber().name(), "홍길동");
        Assertions.assertEquals(orderResponse.subscriber().phoneNumber(), "010-1234-5678");
        Assertions.assertEquals(orderResponse.totalPrice(), 110000);
    }
}