package com.tourgether.domain.recruit.model.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tourgether.domain.recruit.model.dto.QRecruitQueryDto;
import com.tourgether.domain.recruit.model.dto.RecruitQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.tourgether.domain.member.model.entity.QMember.*;
import static com.tourgether.domain.recruit.model.entity.QRecruit.*;

@Repository
@RequiredArgsConstructor
public class RecruitRepositoryImpl implements RecruitRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 최신순
    @Override
    public Page<RecruitQueryDto> findPageWithCreated(Pageable pageable) {
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

    // 수정순
    @Override
    public Page<RecruitQueryDto> findPageWithModified(Pageable pageable) {
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
                .orderBy(recruit.lastModifiedDate.desc())
                .fetch();

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(recruits, pageable, count);
    }

    // 조회순
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
                .orderBy(recruit.view.desc(), recruit.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(recruits, pageable, count);
    }

    // 키워드 최신순
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
                .where(isContain(keyword))
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

    // 키워드 수정순
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
                .where(isContain(keyword))
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

    // 키워드 정확도순
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
                .where(isContain(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<RecruitQueryDto, Integer> countMap = new HashMap<>();
        for (RecruitQueryDto recruit : recruits) {
            int totalCount = countKeyword(keyword, recruit);
            countMap.put(recruit, totalCount);
        }
        List<Map.Entry<RecruitQueryDto, Integer>> countMapList = new ArrayList<>(countMap.entrySet());
        List<RecruitQueryDto> sortedRecruits = new ArrayList<>();

        // 키워드 개수 내림차순 정렬, 같을 시 id 내림차순 정렬
        sortByKeywordNumber(countMapList, sortedRecruits);

        Long count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .fetchOne();

        return new PageImpl<>(sortedRecruits, pageable, count);
    }

    private BooleanExpression isContain(String keyword) {
        return recruit.title.contains(keyword).or(recruit.content.contains(keyword));
    }

    private int countKeyword(String keyword, RecruitQueryDto recruit) {
        int count1 = recruit.getTitle().length() - recruit.getTitle().replace(keyword, "").length();
        int count2 = recruit.getContent().length() - recruit.getContent().replace(keyword, "").length();

        return count1 + count2;
    }

    private void sortByKeywordNumber(List<Map.Entry<RecruitQueryDto, Integer>> countMapList, List<RecruitQueryDto> sortedRecruits) {
        Collections.sort(countMapList, (o1, o2) -> {
            int num;
            if (!o1.getValue().equals(o2.getValue())) {
                num = o2.getValue().compareTo(o1.getValue());
            } else {
                num = o2.getKey().getId().compareTo(o1.getKey().getId());
            }
            return num;
        });
        countMapList.forEach(m -> sortedRecruits.add(m.getKey()));
    }
}
