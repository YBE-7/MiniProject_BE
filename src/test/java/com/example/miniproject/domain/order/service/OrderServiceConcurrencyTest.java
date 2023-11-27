package com.example.miniproject.domain.order.service;

import static com.example.miniproject.global.constant.AccommodationType.HOTEL;
import static com.example.miniproject.global.constant.Region.SEOUL;
import static com.example.miniproject.global.constant.Role.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepository;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.domain.order.dto.request.ClientRequest;
import com.example.miniproject.domain.order.dto.request.OrderItemRegisterRequest;
import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.request.SubscriberRequest;
import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.order.repository.OrderRepository;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.RoomRepository;
import com.example.miniproject.domain.roomtype.repository.RoomTypeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceConcurrencyTest {

    @Autowired
    private OrderServiceFacade orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void beforeEach() {
        Accommodation accommodation = singleAccommodation();
        RoomType roomType = singleRoomType(accommodation);
        Room room = singleRoom(roomType);
    }

    @AfterEach
    void afterEach() {
        orderRepository.deleteAll();
        memberRepository.deleteAll();

        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        accommodationRepository.deleteAll();
    }

    @Test
    void order_concurrency() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Accommodation accommodation = singleAccommodation();
        RoomType roomType = singleRoomType(accommodation);
        Room room1 = singleRoom(roomType);
        //Room room2 = singleRoom(roomType);
        List<Long> memberIds = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            Member member = memberRepository.save(
                Member.create(
                    "email" + i,
                    "password",
                    "name",
                    MEMBER
                )
            );
            memberIds.add(member.getId());
        }

        OrderItemRegisterRequest orderItemRegisterRequest1 = new OrderItemRegisterRequest(
            roomType.getId(),
            LocalDate.of(2023, 12, 1),
            LocalDate.of(2023, 12, 3)
        );
//        OrderItemRegisterRequest orderItemRegisterRequest2 = new OrderItemRegisterRequest(
//            roomType.getId(),
//            LocalDate.of(2023, 11, 30),
//            LocalDate.of(2023, 12, 2)
//        );
        OrderRegisterRequest orderRegisterRequest = new OrderRegisterRequest(
            List.of(orderItemRegisterRequest1),
            new ClientRequest("name", "010"),
            new SubscriberRequest("name", "010"),
            "NAVER"
        );

        for (int i = 0; i < memberIds.size(); i++) {
            execute(executorService, latch, (long) memberIds.get(i), orderRegisterRequest);
        }

        latch.await();

        List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(1);
    }

    private void execute(
        ExecutorService executorService,
        CountDownLatch latch,
        Long memberId,
        OrderRegisterRequest orderRegisterRequest
    ) {
        executorService.submit(() -> {
            try {
                orderService.registerOrder(
                    memberId, orderRegisterRequest
                );
            } finally {
                latch.countDown();
            }
        });
    }

    private Accommodation singleAccommodation() {
        return accommodationRepository.save(
            Accommodation.create(
                HOTEL,
                SEOUL,
                "seoul hotel",
                "소개",
                "와이파이",
                4.0,
                Location.create(
                    "address",
                    123.0,
                    123.0
                ),
                "thumbnail"
            )
        );
    }

    private RoomType singleRoomType(Accommodation accommodation) {
        return roomTypeRepository.save(
            RoomType.create(
                accommodation,
                "standard",
                10000,
                4,
                "소개",
                "와이파이"
            )
        );
    }

    private Room singleRoom(RoomType roomType) {
        return roomRepository.save(
            Room.create(
                roomType,
                "room1"
            )
        );
    }
}