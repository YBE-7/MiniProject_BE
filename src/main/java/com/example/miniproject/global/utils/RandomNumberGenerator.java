package com.example.miniproject.global.utils;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomNumberGenerator {

    public static int generate(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
