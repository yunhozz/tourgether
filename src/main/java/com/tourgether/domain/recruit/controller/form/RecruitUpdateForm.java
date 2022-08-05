package com.tourgether.domain.recruit.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RecruitUpdateForm {

    @NotBlank
    private String recruitId;

    @NotNull
    private Long writerId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public RecruitUpdateForm(String recruitId, Long writerId) {
        this.recruitId = recruitId;
        this.writerId = writerId;
    }
}
