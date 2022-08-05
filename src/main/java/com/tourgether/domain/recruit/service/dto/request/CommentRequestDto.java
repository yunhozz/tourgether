package com.tourgether.domain.recruit.service.dto.request;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Comment;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequestDto {

    @Setter
    private Recruit recruit;

    @Setter
    private Member writer;

    @Setter
    private Comment parent;

    @NotBlank
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }

    public Comment parentToEntity() {
        return Comment.createComment(recruit, writer, content);
    }

    public Comment childToEntity() {
        return Comment.createCommentChild(recruit, writer, parent, content);
    }
}
