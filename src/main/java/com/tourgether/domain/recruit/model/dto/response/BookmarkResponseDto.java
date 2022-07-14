package com.tourgether.domain.recruit.model.dto.response;

import com.tourgether.domain.recruit.model.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkResponseDto {

    private Long id;
    private Long userId;
    private Long recruitId;

    public BookmarkResponseDto(Bookmark bookmark) {
        id = bookmark.getId();
        userId = bookmark.getMember().getId();
        recruitId = bookmark.getRecruit().getId();
    }
}
