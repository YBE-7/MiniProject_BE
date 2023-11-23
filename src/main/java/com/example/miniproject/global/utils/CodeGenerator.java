package com.example.miniproject.global.utils;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeGenerator {

    public static String generate() {
        return UUID.randomUUID()
            .toString()
            .substring(24);
    }
}
