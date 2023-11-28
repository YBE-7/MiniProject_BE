package com.example.miniproject.domain.member.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.domain.cart.dto.request.CartItemRegisterRequest;
import com.example.miniproject.domain.cart.repository.CartRepository;
import com.example.miniproject.domain.cart.service.CartServiceImpl;
import com.example.miniproject.domain.member.dto.request.MemberLoginRequest;
import com.example.miniproject.domain.member.dto.request.MemberSignUpRequest;
import com.example.miniproject.domain.member.dto.response.*;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.domain.order.dto.request.ClientRequest;
import com.example.miniproject.domain.order.dto.request.OrderItemRegisterRequest;
import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.request.SubscriberRequest;
import com.example.miniproject.domain.order.entity.Order;
import com.example.miniproject.domain.order.entity.OrderItem;
import com.example.miniproject.domain.order.repository.OrderRepository;
import com.example.miniproject.domain.order.service.OrderServiceImpl;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.Role;
import com.example.miniproject.global.security.JwtService;
import com.example.miniproject.global.security.MemberDetails;
import com.example.miniproject.global.security.MemberDetailsService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.HttpConstraint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private OrderServiceImpl orderService;
    @InjectMocks
    MemberServiceImpl memberService;
    @Mock
    AuthenticationProvider authenticationProvider;
    @Mock
    JwtService jwtService;
    @Mock
    Authentication authentication;
    @Mock
    MemberRepository memberRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpSuccess() {
        //given
        MemberSignUpRequest signUpRequest = new MemberSignUpRequest("hyem5019@email.com", "qwerasqweras!1", "hyemin");
        Member member = signUpRequest.toEntity();
        ReflectionTestUtils.setField(member,"id",1L);
        when(memberRepository.save(any())).thenReturn(member);

        //when
        MemberSignUpResponse signUpResponse = memberService.signUp(signUpRequest);

        //then
        assertThat(signUpResponse).isNotNull();
        assertThat(signUpResponse.memberId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("회원가입 실패 테스트_이메일 중복")
    void signupFail() {
        //given
        MemberSignUpRequest signUpRequest = new MemberSignUpRequest("tester@email.com", "qwerasqweras!1", "tester");
        when(memberRepository.findByEmail("hyem5019@email.com")).thenReturn(Optional.of(Member.create("hyem5019@email.com", "hyemin", "qwerasqweras!1", Role.MEMBER)));
        //when

        //then
        assertThrows(NullPointerException.class, () -> memberService.signUp(signUpRequest));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void login() {
        //given
        Member member = Member.create("hyem5019@email.com", "hyemin", "qwerasqweras!1", Role.MEMBER);
        ReflectionTestUtils.setField(member, "id", 1L);
        MemberDetails memberDetails = MemberDetails.create(member);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), member.getPassword());

        when(authenticationProvider.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(memberDetails);
        when(memberRepository.getReferenceById(memberDetails.getId())).thenReturn(member);
        when(jwtService.issue(any(),any(),any())).thenReturn(JWT.create()
            .withSubject(String.valueOf(member.getId()))
            .withClaim("email", member.getEmail())
            .withClaim("roles", String.valueOf(member.getRole()))
            .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
            .sign(Algorithm.HMAC256("sercet_key")));

        //when
        MemberLoginResponse memberLoginResponse = memberService.login(memberLoginRequest);

        //then
        assertThat(memberLoginResponse.accessToken()).isNotNull();
        assertThat(String.valueOf(memberLoginResponse).length()).isGreaterThan(10);

    }

    @Test
    @DisplayName("현재 로그인한 사용자의 마이페이지 조회 성공 테스트")
    void getMyPage() {
        //given
        Long first_id = 1L;
        Long second_id = 2L;
        when(memberRepository.getReferenceById(first_id)).thenReturn(Member.create("hyem5019@email.com", "hyemin", "qwerasqweras1!", Role.MEMBER));
        when(memberRepository.getReferenceById(second_id)).thenReturn(Member.create("test@email.com", "tester", "1234asdfasd!", Role.MEMBER));
        //when
        MemberMyPageResponse response = memberService.getMyPage(first_id);
        //then
        assertThat(response.email()).isEqualTo("hyem5019@email.com");
        assertThat(response.name()).isEqualTo("hyemin");
        assertThat(response.email()).isNotEqualTo("test@email.com");
        assertThat(response.name()).isNotEqualTo("tester");
    }

    @Test
    @DisplayName("로그인 사용자 주문내역 조회 성공 테스트")
    @Transactional
    void getOrders() {
        // Given

        // 회원등록
        Member testMember = Member.create(
            "test@test.com",
            "testName",
            "Testpassword5",
            Role.MEMBER
        );
        em.persist(testMember);
        when(memberRepository.getReferenceById(1L)).thenReturn(testMember);

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

        Order order1 = Order.create(testMember, "hyemin", "010-1234-1234", "hyemin", "010-1234-1234", "NAVER", "53edfef");
        OrderItem orderItem1 = OrderItem.create(order1, room1, LocalDate.of(2023, 11, 11), LocalDate.of(2023, 11, 12), "qwerasd123");
        order1.addOrderItem(orderItem1);
        em.persist(order1);
        em.persist(orderItem1);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        when(orderRepository.findByMemberOrderByIdDesc(testMember)).thenReturn(orderList);

        CartItemRegisterRequest cartItemRegisterRequest =
            new CartItemRegisterRequest(
                testRoomType.getId(),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 3)
            );

        List<OrderResponse> orderResponseList = memberService.getOrders(1L);

        //then
        assertThat(orderResponseList).isNotNull();
        assertThat(orderResponseList.get(0).orderDate()).isEqualTo(LocalDate.now());
        assertThat(orderResponseList.get(0).orderItems().get(0).code()).isEqualTo(orderItem1.getCode());

    }
}