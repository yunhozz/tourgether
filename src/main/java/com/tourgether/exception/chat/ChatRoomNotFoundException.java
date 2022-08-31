package com.tourgether.exception.chat;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ChatRoomNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ChatRoomNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
