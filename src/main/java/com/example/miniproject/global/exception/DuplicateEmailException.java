package com.example.miniproject.global.exception;

import com.example.miniproject.global.constant.ErrorCode;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL.getMessage());
    }

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
