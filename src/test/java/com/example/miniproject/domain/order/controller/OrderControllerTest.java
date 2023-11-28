package com.example.miniproject.domain.order.controller;

import com.example.miniproject.domain.member.dto.response.AccommodationResponse;
import com.example.miniproject.domain.member.dto.response.RoomTypeResponse;
import com.example.miniproject.domain.order.dto.request.ClientRequest;
import com.example.miniproject.domain.order.dto.request.OrderItemRegisterRequest;
import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.request.SubscriberRequest;
import com.example.miniproject.domain.order.dto.response.*;
import com.example.miniproject.domain.order.service.OrderService;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.security.WithMockMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.miniproject.global.constant.PaymentMethod.NAVER_PAY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("[API][POST] 주문 테스트")
    @WithMockMember
    @Test
    void registerOrder() throws Exception {
        //given
        String url = "/api/orders";

        OrderRegisterRequest request = new OrderRegisterRequest(
            List.of(
                new OrderItemRegisterRequest(
                    1L,
                    LocalDate.parse("2023-11-28"),
                    LocalDate.parse("2023-11-29")
                )
            ),
            new ClientRequest("홍길동", "010-1234-5678"),
            new SubscriberRequest("홍길동", "010-1234-5678"),
            NAVER_PAY
        );
        OrderRegisterResponse response = new OrderRegisterResponse(1L);
        given(orderService.registerOrder(any(), any()))
            .willReturn(response);


        // when
        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                    .contentType(MEDIA_TYPE)
                    .content(objectMapper.writeValueAsString(request)))

            //then
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @DisplayName("[API][GET] 주문 검색 테스트")
    @WithMockMember
    @Test
    void getOrder() throws Exception {
        // given
        String url = "/api/orders/1";

        OrderResponse response = new OrderResponse(
            1L,
            LocalDateTime.now(),
            new SubscriberResponse("홍길동", "010-1234-5678"),
            new ClientResponse("홍길동", "010-1234-5678"),
            new ReservationInfoResponse("abc", "card"),
            100000,
            List.of(
                new OrderItemResponse(
                    1L,
                    "code",
                    new AccommodationResponse(
                        1L,
                        AccommodationType.HOTEL,
                        "HotelA",
                        "image1"
                    ),
                    new RoomTypeResponse(
                        1L,
                        "StandardA",
                        4
                    ),
                    LocalDate.parse("2023-11-29"),
                    LocalDate.parse("2023-11-30")
                )

            )
        );

        given(orderService.getOrder(any(), any()))
            .willReturn(response);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MEDIA_TYPE))

            // then
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(response.id()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderTime").value(response.orderTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.subscriber.name").value(response.subscriber().name()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.subscriber.phoneNumber").value(response.subscriber().phoneNumber()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.client.name").value(response.client().name()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.client.phoneNumber").value(response.client().phoneNumber()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.reservationInfo.code").value(response.reservationInfo().code()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPrice").value(response.totalPrice()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderItems[0].id")
                .value(response.orderItems().get(0).id()));
    }
}