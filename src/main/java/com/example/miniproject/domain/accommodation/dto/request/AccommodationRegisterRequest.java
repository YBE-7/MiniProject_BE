package com.example.miniproject.domain.accommodation.dto.request;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AccommodationRegisterRequest(

    @NotNull
    AccommodationType type,

    @NotNull
    Region region,

    @NotBlank
    String name,

    @NotBlank
    String introduction,

    @NotBlank
    String service,

    @NotNull
    Double star,

    //추후 Multipart로 교체
    String thumbnailUrl,

    @NotBlank
    String address,

    @NotBlank
    Double latitude,

    @NotBlank
    Double longitude,

    //추후 List<Multipart>로 교체
    @NotNull
    List<String> images
) {
    public Accommodation toEntity() {
        return Accommodation.create(
            type,
            region,
            name,
            introduction,
            service,
            star,
            Location.create(
                address, latitude, longitude
            ),
            thumbnailUrl
        );
    }
}
