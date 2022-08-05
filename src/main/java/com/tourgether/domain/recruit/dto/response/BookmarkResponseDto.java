package com.tourgether.domain.recruit.dto.response;

import com.tourgether.domain.recruit.model.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkResponseDto {

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
