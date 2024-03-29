package com.tourgether.exception.apply;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ApplyNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApplyNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
