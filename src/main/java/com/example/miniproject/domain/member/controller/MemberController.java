package com.example.miniproject.domain.member.controller;

import com.example.miniproject.domain.member.dto.request.MemberLoginRequest;
import com.example.miniproject.domain.member.dto.request.MemberSignUpRequest;
import com.example.miniproject.domain.member.service.MemberService;
import com.example.miniproject.global.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
        @RequestBody @Valid MemberSignUpRequest request
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiUtils.success(
                    memberService.signUp(request)
                )
            );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody @Valid MemberLoginRequest request
    ) {
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(
                    memberService.login(request)
                )
            );
    }
}
