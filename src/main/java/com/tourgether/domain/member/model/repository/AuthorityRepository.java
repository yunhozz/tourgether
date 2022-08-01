package com.tourgether.domain.member.model.repository;

import com.tourgether.domain.member.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
