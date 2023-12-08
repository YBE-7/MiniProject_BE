package com.example.miniproject.global.exception;

import com.example.miniproject.global.constant.ErrorCode;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super(ErrorCode.NOT_FOUND_MEMBER.getMessage());
    }

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
