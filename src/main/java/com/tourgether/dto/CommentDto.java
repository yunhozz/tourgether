package com.tourgether.dto;

import com.tourgether.domain.recruit.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
        private String content;

        public CommentResponseDto(Comment comment) {
            id = comment.getId();
            recruitId = comment.getRecruit().getId();
            writerId = comment.getWriter().getId();
            content = comment.getContent();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateForm {

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;
    }
}
