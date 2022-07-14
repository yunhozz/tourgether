package com.tourgether.domain.recruit.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourgether.domain.recruit.model.dto.QRecruitQueryDto;
import com.tourgether.domain.recruit.model.dto.RecruitQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.tourgether.domain.member.model.QMember.*;
import static com.tourgether.domain.recruit.model.entity.QRecruit.*;

@Repository
@RequiredArgsConstructor
public class RecruitRepositoryImpl implements RecruitRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RecruitQueryDto> findSimplePage(Pageable pageable) {
        List<RecruitQueryDto> recruits = queryFactory
                .select(new QRecruitQueryDto(
                        recruit.id,
                        recruit.title,
                        recruit.content,
                        recruit.view,
                        recruit.createdDate,
                        recruit.lastModifiedDate,
                        member.id,
                        member.nickname,
                        member.profileImgUrl
                ))
                .from(recruit)
                .join(recruit.writer, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruit.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(recruits, pageable, count);
    }

    @Override
    public Page<RecruitQueryDto> findPageWithPopularity(Pageable pageable) {
        List<RecruitQueryDto> recruits = queryFactory
                .select(new QRecruitQueryDto(
                        recruit.id,
                        recruit.title,
                        recruit.content,
                        recruit.view,
                        recruit.createdDate,
                        recruit.lastModifiedDate,
                        member.id,
                        member.nickname,
                        member.profileImgUrl
                ))
                .from(recruit)
                .join(recruit.writer, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruit.view.desc())
                .fetch();

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(recruits, pageable, count);
    }

    @Override
    public Page<RecruitQueryDto> findPageWithKeyword(String keyword, Pageable pageable) {
        List<RecruitQueryDto> recruits = queryFactory
                .select(new QRecruitQueryDto(
                        recruit.id,
                        recruit.title,
                        recruit.content,
                        recruit.view,
                        recruit.createdDate,
                        recruit.lastModifiedDate,
                        member.id,
                        member.nickname,
                        member.profileImgUrl
                ))
                .from(recruit)
                .join(recruit.writer, member)
                .where(recruit.title.contains(keyword)
                        .or(recruit.content.contains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruit.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(recruits, pageable, count);
    }

    @Override
    public Page<RecruitQueryDto> findPageWithKeywordOnLatestOrder(String keyword, Pageable pageable) {
        List<RecruitQueryDto> recruits = queryFactory
                .select(new QRecruitQueryDto(
                        recruit.id,
                        recruit.title,
                        recruit.content,
                        recruit.view,
                        recruit.createdDate,
                        recruit.lastModifiedDate,
                        member.id,
                        member.nickname,
                        member.profileImgUrl
                ))
                .from(recruit)
                .join(recruit.writer, member)
                .where(recruit.title.contains(keyword)
                        .or(recruit.content.contains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruit.lastModifiedDate.desc())
                .fetch();

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(recruits, pageable, count);
    }

    @Override
    public Page<RecruitQueryDto> findPageWithKeywordOnAccuracyOrder(String keyword, Pageable pageable) {
        List<RecruitQueryDto> recruits = queryFactory
                .select(new QRecruitQueryDto(
                        recruit.id,
                        recruit.title,
                        recruit.content,
                        recruit.view,
                        recruit.createdDate,
                        recruit.lastModifiedDate,
                        member.id,
                        member.nickname,
                        member.profileImgUrl
                ))
                .from(recruit)
                .join(recruit.writer, member)
                .where(recruit.title.contains(keyword)
                        .or(recruit.content.contains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<RecruitQueryDto, Integer> countMap = new HashMap<>();
        for (RecruitQueryDto recruit : recruits) {
            int totalCount = countKeyword(keyword, recruit);
            countMap.put(recruit, totalCount);
        }
        List<Map.Entry<RecruitQueryDto, Integer>> countMapList = new ArrayList<>();
        List<RecruitQueryDto> sortedRecruits = new ArrayList<>();

        Collections.sort(countMapList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        countMapList.forEach(m -> sortedRecruits.add(m.getKey()));

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(sortedRecruits, pageable, count);
    }

    private int countKeyword(String keyword, RecruitQueryDto recruit) {
        int count1 = recruit.getTitle().length() - recruit.getTitle().replace(keyword, "").length();
        int count2 = recruit.getContent().length() - recruit.getContent().replace(keyword, "").length();

        return count1 + count2;
    }
}
