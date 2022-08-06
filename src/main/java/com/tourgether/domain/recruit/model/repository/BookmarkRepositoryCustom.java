package com.tourgether.domain.recruit.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.tourgether.dto.BookmarkDto.*;

public interface BookmarkRepositoryCustom {

    Page<BookmarkQueryDto> findPageWithUserId(Long userId, Pageable pageable);
}
