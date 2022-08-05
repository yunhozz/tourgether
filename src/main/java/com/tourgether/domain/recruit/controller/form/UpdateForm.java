package com.tourgether.domain.recruit.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
