package com.tourgether.exception.chat;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ChatNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ChatNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
