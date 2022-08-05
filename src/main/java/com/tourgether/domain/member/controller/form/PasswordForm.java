package com.tourgether.domain.member.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordForm {

    @NotBlank
    private String originalPw;

    @NotBlank
    private String newPw;
}
