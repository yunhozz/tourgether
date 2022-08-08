package com.tourgether.domain.member.model.repository.auth;

import com.tourgether.domain.member.model.entity.auth.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {
}
