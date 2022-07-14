package com.tourgether.domain.recruit.controller;

import com.tourgether.enums.SearchCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SearchForm {

    @NotBlank
    private String keyword;

    private SearchCondition condition;
}
