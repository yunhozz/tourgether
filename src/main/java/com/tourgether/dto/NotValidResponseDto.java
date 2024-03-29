package com.tourgether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotValidResponseDto {

    private String defaultMessage;
    private String field;
    private Object rejectedValue;
    private String code;
}
