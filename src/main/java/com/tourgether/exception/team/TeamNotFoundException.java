package com.tourgether.exception.team;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class TeamNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public TeamNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
