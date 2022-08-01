package com.tourgether.domain.member.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
