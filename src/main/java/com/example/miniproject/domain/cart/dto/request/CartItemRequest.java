package com.example.miniproject.domain.cart.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CartItemRequest(
    @NotNull
    Long roomTypeId,

    @NotNull
    LocalDate checkinDate,

    @NotNull
    LocalDate checkoutDate
) {}
