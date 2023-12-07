package com.example.miniproject.domain.member.service;

import com.example.miniproject.domain.member.dto.request.MemberAccountInfoRequest;
import com.example.miniproject.domain.member.dto.request.MemberLoginRequest;
import com.example.miniproject.domain.member.dto.request.MemberSignUpRequest;
import com.example.miniproject.domain.member.dto.response.*;

import java.util.List;

public interface MemberService {

    MemberSignUpResponse signUp(MemberSignUpRequest request);

    MemberLoginResponse login(MemberLoginRequest request);

    MemberMyPageResponse getMyPage(Long id);

    List<OrderResponse> getOrders(Long id);

    MemberAccountInfoResponse findId(MemberAccountInfoRequest request) throws IllegalAccessException, Exception;
}
