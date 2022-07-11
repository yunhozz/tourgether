package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.recruit.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
