package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.recruit.model.entity.Apply;
import com.tourgether.domain.recruit.model.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    List<Apply> findByMember(Member member);
    List<Apply> findByRecruit(Recruit recruit);
}
