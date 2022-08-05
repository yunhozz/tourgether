package com.tourgether.domain.recruit.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
