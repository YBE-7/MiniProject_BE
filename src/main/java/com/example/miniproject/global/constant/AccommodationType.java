package com.example.miniproject.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccommodationType {
    HOTEL("호텔"),
    RESORT("리조트"),
    PENSION("펜션"),
    POOL_VILLA("풀빌라");

    private final String value;
}
