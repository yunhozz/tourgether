package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.recruit.dto.BookmarkQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkRepositoryCustom {

    Page<BookmarkQueryDto> findPageWithUserId(Long userId, Pageable pageable);
}
