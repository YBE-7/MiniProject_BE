package com.example.miniproject.domain.member.controller;

import com.example.miniproject.domain.member.dto.request.MemberLoginRequest;
import com.example.miniproject.domain.member.dto.request.MemberSignUpRequest;
import com.example.miniproject.domain.member.dto.response.MemberLoginResponse;
import com.example.miniproject.domain.member.dto.response.MemberSignUpResponse;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.domain.member.service.MemberServiceImpl;
import com.example.miniproject.global.constant.Role;
import com.example.miniproject.global.security.JwtService;
import com.example.miniproject.global.security.MemberDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    JwtService jwtService;
    @Autowired
    MemberDetailsService memberDetailsService;
    @Autowired
    MemberRepository memberRepository;

    static final String SIGNUP_URL = "/api/members/signup";
    static final String LOGIN_URL = "/api/members/login";
    static final String MYPAGE_URL = "/api/members/mypage";
    static final String ORDER_URL = "/api/members/orders";

    @Test
    @DisplayName("회원가입 성공 컨트롤러 테스트")
    void signup() throws Exception {
        //given
        Member member = Member.create("hyem5019@email.com", "hyemin", "qwerasqweras!1", Role.MEMBER);
        MemberSignUpRequest memberSignUpRequest = new MemberSignUpRequest(member.getEmail(), member.getPassword(), member.getName());

        //when
        ResultActions resultActions = mockMvc.perform(post(SIGNUP_URL)
                .content(objectMapper.writeValueAsBytes(memberSignUpRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        resultActions
            .andExpect(status().isCreated())
            .andDo(result -> {
                String contentString = result.getResponse().getContentAsString();
                MemberSignUpResponse memberSignUpResponse = objectMapper.readValue(contentString, MemberSignUpResponse.class);
                assert memberSignUpResponse != null;
            });
    }


    @Test
    @DisplayName("로그인 성공 컨트롤러 테스트")
    @Transactional
    void login() throws Exception {

        //given
        Member member = Member.create("hyem5019@email.com", "hyemin", "qwerasqweras!1", Role.MEMBER);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), member.getPassword());
        MemberSignUpRequest memberSignUpRequest = new MemberSignUpRequest(member.getEmail(), member.getPassword(), member.getName());
        memberService.signUp(memberSignUpRequest);

        //when
        ResultActions resultActions = mockMvc.perform(post(LOGIN_URL)
                .content(objectMapper.writeValueAsBytes(memberLoginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        resultActions
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인사용자 mypage 조회 컨트롤러 테스트")
    void getMyPage() throws Exception {

        //given
        Member member = Member.create("hyem5019@email.com", "hyemin", "qwerasqweras!1", Role.MEMBER);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), member.getPassword());
        MemberSignUpRequest memberSignUpRequest = new MemberSignUpRequest(member.getEmail(), member.getPassword(), member.getName());
        memberService.signUp(memberSignUpRequest);
        MemberLoginResponse accessToken = memberService.login(memberLoginRequest);

        //when
        ResultActions resultActions = mockMvc.perform(get(MYPAGE_URL)
                .header("Authorization","Bearer "+accessToken.accessToken())
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        resultActions
            .andExpect(status().isOk());
    }

    @Test
    void getOrders() throws Exception {
        //given
        Member member = Member.create("hyem5019@email.com", "hyemin", "qwerasqweras!1", Role.MEMBER);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), member.getPassword());
        MemberSignUpRequest memberSignUpRequest = new MemberSignUpRequest(member.getEmail(), member.getPassword(), member.getName());
        memberService.signUp(memberSignUpRequest);
        MemberLoginResponse accessToken = memberService.login(memberLoginRequest);

        //when
        ResultActions resultActions = mockMvc.perform(get(ORDER_URL)
                .header("Authorization","Bearer "+accessToken.accessToken())
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        resultActions
            .andExpect(status().isOk());
    }
}