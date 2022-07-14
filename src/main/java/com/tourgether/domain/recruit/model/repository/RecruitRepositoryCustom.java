package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.recruit.model.dto.RecruitQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitRepositoryCustom {

    Page<RecruitQueryDto> findSimplePage(Pageable pageable);
    Page<RecruitQueryDto> findPageWithPopularity(Pageable pageable);
    Page<RecruitQueryDto> findPageWithKeyword(String keyword, Pageable pageable);
    Page<RecruitQueryDto> findPageWithKeywordOnLatestOrder(String keyword, Pageable pageable);
    Page<RecruitQueryDto> findPageWithKeywordOnAccuracyOrder(String keyword, Pageable pageable);
}
