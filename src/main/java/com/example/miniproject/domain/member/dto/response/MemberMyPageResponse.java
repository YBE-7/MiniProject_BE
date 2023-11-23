package com.example.miniproject.domain.member.dto.response;

import com.example.miniproject.domain.member.entity.Member;

public record MemberMyPageResponse(
    Long id,
    String email,
    String name
) {
    public MemberMyPageResponse(Member member) {
        this(member.getId(), member.getEmail(), member.getName());
    }
}
