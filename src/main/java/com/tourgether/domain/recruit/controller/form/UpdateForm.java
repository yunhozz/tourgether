package com.tourgether.domain.recruit.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class UpdateForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
