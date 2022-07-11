package com.tourgether.domain.chat.model.repository;

import com.tourgether.domain.chat.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
