package com.example.miniproject.global.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DaysCalculator {

    public static int calculateDifference(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            return 1;
        }
        return (int) ChronoUnit.DAYS.between(from, to);
    }
}
