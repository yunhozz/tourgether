package com.tourgether.domain.recruit.dto.response;

import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitResponseDto {

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
