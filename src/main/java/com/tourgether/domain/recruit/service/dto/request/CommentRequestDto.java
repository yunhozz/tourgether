package com.tourgether.domain.recruit.service.dto.request;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Comment;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotNull
    private Recruit recruit;

    @NotNull
    private Member writer;

    @NotNull
    private Comment parent;

    @NotBlank
    private String content;

    @Builder
    private CommentRequestDto(Recruit recruit, Member writer, Comment parent, String content) {
        this.recruit = recruit;
        this.writer = writer;
        this.parent = parent;
        this.content = content;
    }

    public Comment parentToEntity() {
        return Comment.createComment(recruit, writer, content);
    }

    public Comment childToEntity() {
        return Comment.createCommentChild(recruit, writer, parent, content);
    }
}
