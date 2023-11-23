package com.example.miniproject.domain.member.service;


import com.example.miniproject.domain.member.dto.request.MemberLoginRequest;
import com.example.miniproject.domain.member.dto.request.MemberSignUpRequest;
import com.example.miniproject.domain.member.dto.response.MemberLoginResponse;
import com.example.miniproject.domain.member.dto.response.MemberMypageResponse;
import com.example.miniproject.domain.member.dto.response.MemberSignUpResponse;
import com.example.miniproject.global.security.MemberDetails;

public interface MemberService {

    MemberSignUpResponse signUp(MemberSignUpRequest request);

    MemberLoginResponse login(MemberLoginRequest request);

    MemberMypageResponse getMypage(MemberDetails memberDetails);
}
