package com.tourgether.exception.member;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class NicknameDuplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    public NicknameDuplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
