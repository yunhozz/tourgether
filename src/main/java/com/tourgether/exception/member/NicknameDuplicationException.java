package com.tourgether.exception.member;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class NicknameDuplicationException extends RuntimeException {

    private ErrorCode errorCode;

    public NicknameDuplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NicknameDuplicationException() {
        super();
    }

    public NicknameDuplicationException(String message) {
        super(message);
    }

    public NicknameDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NicknameDuplicationException(Throwable cause) {
        super(cause);
    }

    protected NicknameDuplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
