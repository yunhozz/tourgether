package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.recruit.model.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
}
