package com.example.miniproject.domain.roomtype.controller;

import static com.example.miniproject.global.constant.ErrorCode.*;
import static com.example.miniproject.global.constant.ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.miniproject.domain.roomtype.dto.response.RoomTypesRegisterResponse;
import com.example.miniproject.domain.roomtype.service.RoomTypeService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RoomTypeControllerTest {

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomTypeService roomTypeService;

    @DisplayName("[API][POST] 객실 타입 등록 - 정상적인 등록 요청일 경우, 정상적으로 등록 결과를 응답합니다.")
    @Test
    void register_success() throws Exception {
        //given
        String url = "/api/roomtypes";
        String request = """
            {
              "data":[
                {
                  "accommodationId":"1",
                  "name":"프리미엄 킹",
                  "price":"330000",
                  "capacity":"4",
                  "introduction":"스트리밍 서비스, 무료주차, 9평형 객실",
                  "service":"목욕시설,TV,냉장고,테이블",
                  "images":["http://tong.visitkorea.or.kr/cms/resource/03/2639703_image2_1.jpg"]
                }
              ]
            }
            """;
        given(roomTypeService.register(any()))
            .willReturn(new RoomTypesRegisterResponse(List.of()));

        //when
        mockMvc.perform(post(url)
                .content(request)
                .contentType(MEDIA_TYPE)
            )

            //then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true));
    }

    @DisplayName("[API][POST] 객실 타입 등록 - 유효하지 않은 등록 요청일 경우, 예외 메시지를 응답합니다.")
    @Test
    void register_fail_validation() throws Exception {
        //given
        String url = "/api/roomtypes";
        String request = """
            {
              "data":[
                {
                  "accommodationId":"1",
                  "name":"프리미엄 킹",
                  "price":"price error",
                  "capacity":"4",
                  "introduction":"스트리밍 서비스, 무료주차, 9평형 객실",
                  "service":"목욕시설,TV,냉장고,테이블",
                  "images":["http://tong.visitkorea.or.kr/cms/resource/03/2639703_image2_1.jpg"]
                }
              ]
            }
            """;

        //when
        mockMvc.perform(post(url)
                .content(request)
                .contentType(MEDIA_TYPE)
            )

            //then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.error.message").value(PARAM_PARSING_MAPPING_ERROR.getMessage()));
    }

    @DisplayName("[API][GET] 객실 타입 단일 조회 - 정상적인 요청일 경우, 정상적으로 객실 타입을 응답합니다.")
    @Test
    void get_success() throws Exception {
        //given
        long roomTypeId = 1L;
        String url = "/api/roomtypes/" + roomTypeId;
        given(roomTypeService.getRoomType(any()))
            .willReturn(null);

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.error").doesNotExist());
    }

    @DisplayName("[API][GET] 객실 타입 단일 조회 - 유효하지 않은 id일 경우, 예외 메시지를 응답합니다.")
    @Test
    void get_fail_id() throws Exception {
        //given
        String roomTypeId = "id1";
        String url = "/api/roomtypes/" + roomTypeId;

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.error.message").value(METHOD_ARGUMENT_TYPE_MISMATCH.getMessage()));
    }
}