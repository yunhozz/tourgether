package com.tourgether.domain.chat.model.repository;

import com.tourgether.domain.chat.model.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
