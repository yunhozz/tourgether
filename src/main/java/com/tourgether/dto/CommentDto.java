package com.tourgether.dto;

import com.tourgether.domain.recruit.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentRequestDto {

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponseDto {

        private Long id;
        private Long recruitId;
        private Long writerId;
        private Long parentId;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public CommentResponseDto(Comment comment) {
            id = comment.getId();
            recruitId = comment.getRecruit().getId();
            writerId = comment.getWriter().getId();
            content = comment.getContent();
            parentId = comment.getParent() != null ? comment.getParent().getId() : null;
            createdDate = comment.getCreatedDate();
            lastModifiedDate = comment.getLastModifiedDate();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateForm {

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;
    }
}
