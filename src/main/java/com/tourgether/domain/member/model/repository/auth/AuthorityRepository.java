package com.tourgether.domain.member.model.repository.auth;

import com.tourgether.domain.member.model.entity.auth.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
