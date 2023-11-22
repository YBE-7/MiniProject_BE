package com.example.miniproject.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
    @NotBlank
    String email,

    @NotBlank
    String password
) {}
