package com.example.miniproject.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SubscriberRequest(

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,16}$")
    String name,

    @NotBlank
    @Pattern(regexp = "^([0-9]{3})-([0-9]{3,4})-([0-9]{4})$")
    String phoneNumber
) {

}
