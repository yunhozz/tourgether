package com.tourgether.dto;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Comment;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentRequestDto {

        @NotNull
        private Long recruitId;

        @NotNull
        private Long writerId;

        private Long parentId;

        @NotBlank
        private String content;

        public Comment parentToEntity(Recruit recruit, Member writer) {
            return Comment.createComment(recruit, writer, content);
        }

        public Comment childToEntity(Recruit recruit, Member writer, Comment parent) {
            return Comment.createCommentChild(recruit, writer, parent, content);
        }
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

        @NotBlank
        private String recruitId;

        @NotBlank
        private String commentId;

        @NotNull
        private Long writerId;

        @NotBlank
        private String content;

        public UpdateForm(String recruitId, String commentId, Long writerId) {
            this.recruitId = recruitId;
            this.commentId = commentId;
            this.writerId = writerId;
        }
    }
}
