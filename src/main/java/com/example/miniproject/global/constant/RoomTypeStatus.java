package com.example.miniproject.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomTypeStatus {
    OK(2), OVER_CAPACITY(1), NO_STOCK(0);

    private final int value;
}
