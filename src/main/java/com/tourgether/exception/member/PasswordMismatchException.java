package com.tourgether.exception.member;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public PasswordMismatchException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
