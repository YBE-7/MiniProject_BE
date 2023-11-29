package com.example.miniproject.domain.cart.controller;

import com.example.miniproject.domain.cart.dto.request.CartItemRegisterRequest;
import com.example.miniproject.domain.cart.dto.response.CartItemRegisterResponse;
import com.example.miniproject.domain.cart.service.CartService;
import com.example.miniproject.global.security.JwtService;
import com.example.miniproject.global.security.MemberDetailsService;
import com.example.miniproject.global.security.WithMockMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private MemberDetailsService memberDetailsService;

    @MockBean
    private CartService cartService;

    @Test
    @WithMockMember
    void 장바구니_상품조회_테스트() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/carts")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @WithMockMember
    void 장바구니_상품등록_테스트() throws Exception {
        CartItemRegisterRequest request = new CartItemRegisterRequest(
            1L,
            LocalDate.of(2023, 12, 1),
            LocalDate.of(2023, 12, 10)
        );
        String content = objectMapper.writeValueAsString(request);

        CartItemRegisterResponse response = new CartItemRegisterResponse(1L);
        when(cartService.registerCartItem(any(), any())).thenReturn(response);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/carts")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @WithMockMember
    void 장바구니_상품삭제_테스트() throws Exception {
        Long cartItemId = 1L;

        doNothing().when(cartService).deleteCartItem(any(), any());

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/carts/" + cartItemId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @WithMockMember
    void 장바구니_모든상품삭제_테스트() throws Exception {
        doNothing().when(cartService).deleteAllCartItems(any());

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/carts")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }
}