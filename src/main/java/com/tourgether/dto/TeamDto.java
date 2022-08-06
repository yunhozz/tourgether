package com.tourgether.dto;

import com.tourgether.domain.member.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TeamDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamRequestDto {

        @NotBlank
        private String name;

        @NotBlank
        private String introduction;

        public Team toEntity() {
            return new Team(name, introduction);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamResponseDto {

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
}
