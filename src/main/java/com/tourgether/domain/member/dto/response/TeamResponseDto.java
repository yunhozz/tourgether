package com.tourgether.domain.member.dto.response;

import com.tourgether.domain.member.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDto {

    private Long id;
    private String name;
    private int numOfMember;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public TeamResponseDto(Team team) {
        id = team.getId();
        name = team.getName();
        createdDate = team.getCreatedDate();
        lastModifiedDate = team.getLastModifiedDate();
    }
}
