package com.tourgether.exception.recruit;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public CommentNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CommentNotFoundException() {
        super();
    }

    public CommentNotFoundException(String message) {
        super(message);
    }

    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CommentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
