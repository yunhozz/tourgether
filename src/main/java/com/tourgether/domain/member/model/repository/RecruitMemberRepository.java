package com.tourgether.domain.member.model.repository;

import com.tourgether.domain.member.model.entity.RecruitMember;
import com.tourgether.domain.recruit.model.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitMemberRepository extends JpaRepository<RecruitMember, Long> {

    List<RecruitMember> findByRecruit(Recruit recruit);
}
