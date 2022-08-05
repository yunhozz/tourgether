package com.tourgether.domain.recruit.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkQueryDto {

    // bookmark
    private Long id;
    private Long userId;
    private LocalDateTime createDate;

    // recruit
    private Long recruitId;
    private Long writerId;
    private String title;

    @QueryProjection
    public BookmarkQueryDto(Long id, Long userId, LocalDateTime createDate, Long recruitId, Long writerId, String title) {
        this.id = id;
        this.userId = userId;
        this.createDate = createDate;
        this.recruitId = recruitId;
        this.writerId = writerId;
        this.title = title;
    }
}
