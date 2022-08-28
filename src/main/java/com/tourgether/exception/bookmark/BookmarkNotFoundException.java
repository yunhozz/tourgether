package com.tourgether.exception.bookmark;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BookmarkNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public BookmarkNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BookmarkNotFoundException() {
        super();
    }

    public BookmarkNotFoundException(String message) {
        super(message);
    }

    public BookmarkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookmarkNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BookmarkNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
