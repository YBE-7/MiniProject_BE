package com.example.miniproject.global.utils;

import com.example.miniproject.global.result.ApiResult;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtils {
    public static <T> ApiResult<T> success(T response) {
        return ApiResult.res(true, response, null);
    }

    public static ApiResult<?> error(Throwable throwable) {
        return ApiResult.res(false, null, ApiResult.ApiError.of(throwable));
    }

    public static ApiResult<?> error(String message) {
        return ApiResult.res(false, null, ApiResult.ApiError.of(message));
    }

    public static ApiResult<?> error(
        String message,
        BindingResult bindingResult,
        MessageSource messageSource,
        Locale locale
    ) {
        return ApiResult.res(
            false,
            null,
            ApiResult.ApiError.of(message, bindingResult, messageSource, locale)
        );
    }

}
