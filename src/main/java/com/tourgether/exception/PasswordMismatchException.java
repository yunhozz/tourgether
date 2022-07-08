package com.tourgether.exception;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {

    private ErrorCode errorCode;

    public PasswordMismatchException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PasswordMismatchException() {
        super();
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }

    protected PasswordMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
