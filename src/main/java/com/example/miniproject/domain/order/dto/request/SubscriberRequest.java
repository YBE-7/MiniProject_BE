package com.example.miniproject.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SubscriberRequest(

    @NotBlank
    String name,

    @NotBlank
    String phoneNumber
) {

}
