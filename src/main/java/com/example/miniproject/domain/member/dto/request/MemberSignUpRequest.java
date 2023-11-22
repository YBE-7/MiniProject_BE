package com.example.miniproject.domain.member.dto.request;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.global.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberSignUpRequest(
    @NotBlank
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    String email,

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")
    String password,

    @NotBlank
    String name
) {
    public Member toEntity() {
        return Member.create(
            this.email,
            this.password,
            this.name,
            Role.MEMBER
        );
    }
}
