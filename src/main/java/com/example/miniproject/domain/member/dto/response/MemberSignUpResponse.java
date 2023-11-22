package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.member.entity.Member;

public record MemberSignUpResponse(
    Long memberId
) {
    public MemberSignUpResponse(Member member) {
        this(member.getId());
    }
}
