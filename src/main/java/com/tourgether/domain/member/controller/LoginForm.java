package com.tourgether.domain.member.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class LoginForm {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
