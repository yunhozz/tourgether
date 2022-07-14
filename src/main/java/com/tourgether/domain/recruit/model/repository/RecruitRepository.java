package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.recruit.model.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitRepository extends JpaRepository<Recruit, Long>, RecruitRepositoryCustom {

    @Modifying(clearAutomatically = true)
    @Query("update Recruit r set r.view = r.view + 1 where r.id = :id")
    void addView(@Param("id") Long id);
}
