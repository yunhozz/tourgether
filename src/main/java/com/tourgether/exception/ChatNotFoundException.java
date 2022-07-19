package com.tourgether.exception;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ChatNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public ChatNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ChatNotFoundException() {
    }

    public ChatNotFoundException(String message) {
        super(message);
    }

    public ChatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatNotFoundException(Throwable cause) {
        super(cause);
    }

    public ChatNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
