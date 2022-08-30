package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.recruit.model.entity.Bookmark;
import com.tourgether.domain.recruit.model.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

    List<Bookmark> findByRecruit(Recruit recruit);

    @Modifying(clearAutomatically = true)
    @Query("delete from Bookmark b where b.id = :id")
    void deleteBookmark(@Param("id") Long id);
}
