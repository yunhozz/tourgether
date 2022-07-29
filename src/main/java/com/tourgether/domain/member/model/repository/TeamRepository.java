package com.tourgether.domain.member.model.repository;

import com.tourgether.domain.member.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
