package com.tourgether.domain.member.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateForm {

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    private String profileUrl;

    public UpdateForm(Long userId) {
        this.userId = userId;
    }
}
