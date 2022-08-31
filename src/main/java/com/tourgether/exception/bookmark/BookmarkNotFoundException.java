package com.tourgether.exception.bookmark;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BookmarkNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public BookmarkNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
