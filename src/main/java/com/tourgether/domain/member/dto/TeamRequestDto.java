package com.tourgether.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class TeamRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String introduction;
}
