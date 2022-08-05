package com.tourgether.domain.chat.model.repository;

import com.tourgether.domain.chat.model.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
}
