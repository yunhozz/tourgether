package com.tourgether.domain.member.model.repository.auth;

import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {

    Set<MemberAuthority> findByMember(Member member);
}
