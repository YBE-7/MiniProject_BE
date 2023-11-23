package com.example.miniproject.global.exception;

import com.example.miniproject.global.constant.ErrorCode;

public class AccessForbiddenException extends RuntimeException {
    public AccessForbiddenException() {
        super(ErrorCode.ACCESS_FORBIDDEN.getMessage());
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
