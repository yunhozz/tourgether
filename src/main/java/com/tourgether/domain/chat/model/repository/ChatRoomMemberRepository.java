package com.tourgether.domain.chat.model.repository;

import com.tourgether.domain.chat.model.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
