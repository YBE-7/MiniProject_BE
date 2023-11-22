package com.example.miniproject.global.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum Role {
    MEMBER, ADMIN;

    @JsonCreator
    public static Role parse(String inputValue) {
        return Stream.of(Role.values())
            .filter(role -> role.toString().equals(inputValue.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
