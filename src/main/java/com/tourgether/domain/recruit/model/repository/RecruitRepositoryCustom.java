package com.tourgether.domain.recruit.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.tourgether.dto.RecruitDto.*;

public interface RecruitRepositoryCustom {

    Page<RecruitQueryDto> findPageWithCreated(Pageable pageable); // 최신 순
    Page<RecruitQueryDto> findPageWithModified(Pageable pageable); // 수정 순
    Page<RecruitQueryDto> findPageWithPopularity(Pageable pageable); // 인기 순
    Page<RecruitQueryDto> findPageWithKeywordOnLatestOrder(String keyword, Pageable pageable); // 검색 + 최신 순
    Page<RecruitQueryDto> findPageWithKeywordOnAccuracyOrder(String keyword, Pageable pageable); // 검색 + 정확도 순
}
