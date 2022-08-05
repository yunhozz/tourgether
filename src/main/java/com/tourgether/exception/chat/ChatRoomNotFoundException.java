package com.tourgether.exception.chat;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ChatRoomNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public ChatRoomNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ChatRoomNotFoundException() {
        super();
    }

    public ChatRoomNotFoundException(String message) {
        super(message);
    }

    public ChatRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatRoomNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ChatRoomNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
