package com.tourgether.domain.recruit.model.repository;

import com.tourgether.domain.recruit.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
