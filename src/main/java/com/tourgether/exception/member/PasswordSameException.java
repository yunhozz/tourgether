package com.tourgether.exception.member;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordSameException extends RuntimeException {

    private ErrorCode errorCode;

    public PasswordSameException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PasswordSameException() {
        super();
    }

    public PasswordSameException(String message) {
        super(message);
    }

    public PasswordSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordSameException(Throwable cause) {
        super(cause);
    }

    protected PasswordSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
