package com.example.miniproject.global.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Locale;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@ToString
@Getter
public class ApiResult<T> {
    private final boolean success;
    private final T data;
    private final ApiError<?> error;

    @Builder
    private ApiResult(final boolean success, final T data, final ApiError<?> error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResult<T> res(final boolean success, final T data, ApiError<?> error) {
        return ApiResult.<T>builder()
            .success(success)
            .data(data)
            .error(error)
            .build();
    }

    @ToString
    @Getter
    public static class ApiError<T> {
        private final String message;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private final T data;

        private ApiError(String message, T data) {
            this.message = message;
            this.data = data;
        }

        public static ApiError<?> of(String message) {
            return new ApiError<>(message, null);
        }

        public static ApiError<?> of(Throwable throwable) {
            return new ApiError<>(throwable.getMessage(), null);
        }

        public static ApiError<?> of(
            String message,
            BindingResult bindingResult,
            MessageSource messageSource,
            Locale locale
        ) {
            List<ValidationError> data = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> ValidationError.of(fieldError, messageSource, locale))
                .toList();

            return new ApiError<>(message, data);
        }
    }

    public record ValidationError(String field, String message) {
        public static ValidationError of(
            final FieldError fieldError,
            MessageSource messageSource,
            Locale locale
        ) {
            return new ValidationError(
                fieldError.getField(),
                messageSource.getMessage(fieldError, locale)
            );
        }
    }
}
