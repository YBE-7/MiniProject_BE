package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.member.entity.Member;

public record MemberMypageResponse(
    Long memberId,
    String memberEmail,
    String memberName
) {
    public MemberMypageResponse(Member member) {
        this(member.getId(), member.getEmail(), member.getName());
    }
}
