package com.tourgether.domain.recruit.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourgether.dto.QBookmarkDto_BookmarkQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tourgether.domain.recruit.model.entity.QBookmark.*;
import static com.tourgether.domain.recruit.model.entity.QRecruit.*;
import static com.tourgether.dto.BookmarkDto.*;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BookmarkQueryDto> findPageWithUserId(Long userId, Pageable pageable) {
        List<BookmarkQueryDto> bookmarks = queryFactory
                .select(new QBookmarkDto_BookmarkQueryDto(
                        bookmark.id,
                        bookmark.member.id,
                        bookmark.createdDate,
                        recruit.id,
                        recruit.writer.id,
                        recruit.title
                ))
                .from(bookmark)
                .join(bookmark.recruit, recruit)
                .where(bookmark.member.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookmark.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(bookmark.count())
                .from(bookmark)
                .fetchOne();

        return new PageImpl<>(bookmarks, pageable, count);
    }
}
