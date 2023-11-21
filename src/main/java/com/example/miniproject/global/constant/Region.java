package com.example.miniproject.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
    SEOUL("서울"),
    BUSAN("부산"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    GANGWON("강원"),
    GYEONGSANG("경상"),
    JEOLLA("전라"),
    CHUNGCHEONG("충청");

    private final String value;
}