package com.tourgether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class TokenDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenRequestDto {

        @NotBlank
        private String accessToken;

        @NotBlank
        private String refreshToken;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenResponseDto {

        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long accessTokenExpireDate;
    }
}
