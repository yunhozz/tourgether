package com.tourgether.exception.member;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordSameException extends RuntimeException {

    private final ErrorCode errorCode;

    public PasswordSameException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
