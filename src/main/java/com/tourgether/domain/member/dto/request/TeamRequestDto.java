package com.tourgether.domain.member.dto.request;

import com.tourgether.domain.member.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String introduction;

    public Team toEntity() {
        return new Team(name, introduction);
    }
}
