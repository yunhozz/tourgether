package com.tourgether.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Recruit;
import com.tourgether.enums.SearchCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.tourgether.dto.CommentDto.*;

public class RecruitDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecruitRequestDto {

        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecruitResponseDto {

        private Long id;
        private Long writerId;
        private String title;
        private String content;
        private int view;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
        private List<CommentResponseDto> comments;

        public RecruitResponseDto(Recruit recruit) {
            id = recruit.getId();
            writerId = recruit.getWriter().getId();
            title = recruit.getTitle();
            content = recruit.getContent();
            view = recruit.getView();
            createdDate = recruit.getCreatedDate();
            lastModifiedDate = recruit.getLastModifiedDate();
            comments = recruit.getComments().stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RecruitQueryDto {

        // recruit
        private Long id;
        private String title;
        private String content;
        private int view;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        // writer
        private Long writerId;
        private String nickname;
        private String profileImgUrl;

        @QueryProjection
        public RecruitQueryDto(Long id, String title, String content, int view, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Long writerId, String nickname, String profileImgUrl) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.view = view;
            this.createdDate = createdDate;
            this.lastModifiedDate = lastModifiedDate;
            this.writerId = writerId;
            this.nickname = nickname;
            this.profileImgUrl = profileImgUrl;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchForm {

        @NotBlank
        private String keyword;

        @NotNull
        private SearchCondition condition;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateForm {

        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }
}
