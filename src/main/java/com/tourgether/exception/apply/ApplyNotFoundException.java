package com.tourgether.exception.apply;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ApplyNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public ApplyNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplyNotFoundException() {
        super();
    }

    public ApplyNotFoundException(String message) {
        super(message);
    }

    public ApplyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplyNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ApplyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
