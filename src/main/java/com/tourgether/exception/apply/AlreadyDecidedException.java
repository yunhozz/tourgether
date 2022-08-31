package com.tourgether.exception.apply;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyDecidedException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyDecidedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
