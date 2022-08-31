package com.tourgether.exception.recruit;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommentNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
