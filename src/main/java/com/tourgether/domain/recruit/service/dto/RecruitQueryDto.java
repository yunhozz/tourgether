package com.tourgether.domain.recruit.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecruitQueryDto {

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
