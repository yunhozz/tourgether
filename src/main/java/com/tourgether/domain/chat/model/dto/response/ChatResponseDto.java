package com.tourgether.domain.chat.model.dto.response;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatResponseDto {

    private Long id;
    private Long senderId;
    private Long chatRoomId;
    private MessageType type;
    private String message;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public ChatResponseDto(Chat chat) {
        id = chat.getId();
        senderId = chat.getSender().getId();
        chatRoomId = chat.getChatRoom().getId();
        type = chat.getType();
        message = chat.getMessage();
        createdDate = chat.getCreatedDate();
        lastModifiedDate = chat.getLastModifiedDate();
    }
}
