package com.tourgether.exception;

import com.tourgether.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private int status;
    private String code;
    private String message;

    public ErrorResponseDto(ErrorCode errorCode) {
        status = errorCode.getStatus();
        code = errorCode.getCode();
        message = errorCode.getMessage();
    }
}
