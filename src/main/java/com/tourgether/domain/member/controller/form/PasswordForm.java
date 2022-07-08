package com.tourgether.domain.member.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class PasswordForm {

    @NotBlank
    private String originalPw;

    @NotBlank
    private String newPw;
}
