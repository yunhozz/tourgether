package com.tourgether.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.tourgether.domain.recruit.model.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class BookmarkDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkResponseDto {

        private Long id;
        private Long userId;
        private Long recruitId;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public BookmarkResponseDto(Bookmark bookmark) {
            id = bookmark.getId();
            userId = bookmark.getMember().getId();
            recruitId = bookmark.getRecruit().getId();
            createdDate = bookmark.getCreatedDate();
            lastModifiedDate = bookmark.getLastModifiedDate();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class BookmarkQueryDto {

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
}
