package com.tourgether.domain.recruit.controller.form;

import com.tourgether.enums.SearchCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SearchForm {

    @NotBlank
    private String keyword;

    @NotNull
    private SearchCondition condition;
}
