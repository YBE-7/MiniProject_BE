package com.example.miniproject.domain.cart.repository;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.cart.entity.CartItem;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.domain.roomtype.repository.QueryDslTestConfig;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(QueryDslTestConfig.class)
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("원하는 날짜의 객실 찾기")
    void findByMemberAndRoomTypeIdAndCheckinDateAndCheckoutDate() {
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

        CartItem testCartItem = CartItem.create(
            testMember,
            testRoomType,
            LocalDate.of(2023,12,1),
            LocalDate.of(2023,12,3)
        );
        em.persist(testCartItem);

        assertThat(cartRepository
            .findByMemberAndRoomTypeIdAndCheckinDateAndCheckoutDate(
                testMember, testRoomType.getId(),
                LocalDate.of(2023,12,1),
                LocalDate.of(2023,12,3)
            ).size()).isEqualTo(1);

        assertThat(cartRepository
            .findByMemberAndRoomTypeIdAndCheckinDateAndCheckoutDate(
                testMember, testRoomType.getId(),
                LocalDate.of(2023,12,1),
                LocalDate.of(2023,12,3)
            ).get(0).getId()).isEqualTo(testCartItem.getId());
    }
}