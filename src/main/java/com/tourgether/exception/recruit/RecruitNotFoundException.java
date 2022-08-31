package com.tourgether.exception.recruit;

import com.tourgether.enums.ErrorCode;
import lombok.Getter;

@Getter
public class RecruitNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public RecruitNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
