package com.tourgether.domain.team.model.repository;

import com.tourgether.domain.team.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
