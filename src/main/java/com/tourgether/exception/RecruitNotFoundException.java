package com.tourgether.exception;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class RecruitNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public RecruitNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RecruitNotFoundException() {
        super();
    }

    public RecruitNotFoundException(String message) {
        super(message);
    }

    public RecruitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecruitNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RecruitNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
