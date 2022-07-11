package com.tourgether.domain.chat.model.repository.chatroom;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
