package com.tourgether.domain.member.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PasswordForm {

    @NotNull
    private Long userId;

    @NotBlank
    private String originalPw;

    @NotBlank
    private String newPw;

    public PasswordForm(Long userId) {
        this.userId = userId;
    }
}
