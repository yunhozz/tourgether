package com.tourgether.domain.recruit.service.dto.response;

import com.tourgether.domain.recruit.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

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
