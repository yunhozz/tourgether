package com.tourgether.exception.recruit;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class WriterMismatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public WriterMismatchException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
