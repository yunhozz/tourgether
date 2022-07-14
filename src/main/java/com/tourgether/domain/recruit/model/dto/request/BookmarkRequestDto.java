package com.tourgether.domain.recruit.model.dto.request;

import com.tourgether.domain.member.model.Member;
import com.tourgether.domain.recruit.model.entity.Bookmark;
import com.tourgether.domain.recruit.model.entity.Recruit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkRequestDto {

    private Member member;
    private Recruit recruit;

    public Bookmark toEntity() {
        return new Bookmark(member, recruit);
    }
}
