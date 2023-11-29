package com.example.miniproject.domain.accommodation.controller;

import static com.example.miniproject.global.constant.ErrorCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.miniproject.domain.accommodation.dto.response.AccommodationPageResponse;
import com.example.miniproject.domain.accommodation.service.AccommodationService;
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
class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccommodationService accommodationService;

    @DisplayName("[API][GET] 숙소 검색 - 검색 쿼리가 올바를 경우, 정상적으로 응답합니다.")
    @Test
    void search_success() throws Exception {
        //given
        String url = "/api/accommodations?type=HOTEL&region=SEOUL";
        given(accommodationService.search(any()))
            .willReturn(new AccommodationPageResponse(
                List.of(),
                0,
                1
            ));

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.error").doesNotExist());
    }

    @DisplayName("[API][GET] 숙소 검색 - 검색 쿼리가 올바르지 않을 경우, 오류 메시지를 응답합니다")
    @Test
    void search_fail_validation() throws Exception {
        //given
        String url = "/api/accommodations?type=TTT&region=RRR";

        //when
        mockMvc.perform(get(url))

            //then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.error.message").value(BEAN_VALIDATION_ERROR.getMessage()));
    }
}