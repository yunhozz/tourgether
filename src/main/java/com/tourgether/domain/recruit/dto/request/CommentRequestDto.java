package com.tourgether.domain.recruit.dto.request;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Comment;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

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
