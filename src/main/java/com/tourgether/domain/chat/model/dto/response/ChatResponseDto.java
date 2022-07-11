package com.tourgether.domain.chat.model.dto.response;

import com.tourgether.domain.chat.model.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatResponseDto {

    private Long id;
    private Long senderId;
    private Long chatRoomId;
    private String message;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public ChatResponseDto(Chat chat) {
        id = chat.getId();
        senderId = chat.getSender().getId();
        chatRoomId = chat.getChatRoom().getId();
        message = chat.getMessage();
        createdDate = chat.getCreatedDate();
        lastModifiedDate = chat.getLastModifiedDate();
    }
}
