package com.example.miniproject.global.utils;

import com.example.miniproject.global.exception.InvalidParamException;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleValidator {

    private static final String ERROR_MESSAGE = "시작 날짜는 끝 날짜보다 이전이어야 합니다.";

    public static void validate(LocalDate from, LocalDate to) {
        if (from.isEqual(to) || from.isAfter(to)) {
            throw new InvalidParamException(ERROR_MESSAGE);
        }
    }
}
