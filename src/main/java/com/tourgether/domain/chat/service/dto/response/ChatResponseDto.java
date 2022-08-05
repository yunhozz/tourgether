package com.tourgether.domain.chat.service.dto.response;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {

    private Long id;
    private Long senderId;
    private Long chatRoomId;
    private String message;
    private MessageType type;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public ChatResponseDto(Chat chat) {
        id = chat.getId();
        senderId = chat.getSender().getId();
        chatRoomId = chat.getChatRoom().getId();
        message = chat.getMessage();
        type = chat.getType();
        createdDate = chat.getCreatedDate();
        lastModifiedDate = chat.getLastModifiedDate();
    }
}