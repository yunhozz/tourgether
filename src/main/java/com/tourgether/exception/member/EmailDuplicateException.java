package com.tourgether.exception.member;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException {

    private final ErrorCode errorCode;

    public EmailDuplicateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
