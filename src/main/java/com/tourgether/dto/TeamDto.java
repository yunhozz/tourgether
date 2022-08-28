package com.tourgether.dto;

import com.tourgether.domain.member.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class TeamDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamRequestDto {

        @NotBlank(message = "팀명을 입력해주세요.")
        private String name;

        private String introduction;

        private String propensities;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamResponseDto {

        private Long id;
        private String name;
        private String introduction;
        private String propensities;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public TeamResponseDto(Team team) {
            id = team.getId();
            name = team.getName();
            introduction = team.getIntroduction();
            propensities = team.getPropensities();
            createdDate = team.getCreatedDate();
            lastModifiedDate = team.getLastModifiedDate();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateForm {

        private String introduction;

        private List<String> propensities;
    }
}
