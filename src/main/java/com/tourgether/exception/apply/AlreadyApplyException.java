package com.tourgether.exception.apply;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyApplyException extends RuntimeException {

    private final ErrorCode errorCode;

    public AlreadyApplyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
