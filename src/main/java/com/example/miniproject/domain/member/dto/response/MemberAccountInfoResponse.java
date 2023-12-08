package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;

public record MemberAccountInfoResponse(
    String email
) {
    public MemberAccountInfoResponse(Member member) {
        this(member.getEmail());
    }
}
