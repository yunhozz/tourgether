package com.tourgether.exception.recruit;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class WriterMismatchException extends RuntimeException {

    private ErrorCode errorCode;

    public WriterMismatchException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public WriterMismatchException() {
        super();
    }

    public WriterMismatchException(String message) {
        super(message);
    }

    public WriterMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterMismatchException(Throwable cause) {
        super(cause);
    }

    protected WriterMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
