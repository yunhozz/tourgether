package com.tourgether.dto;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ChatDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRequestDto {

        @NotNull
        private Long senderId;

        @NotNull
        private Long chatRoomId;

        @NotBlank
        private String message;

        @NotNull
        private MessageType type;

        public Chat toEntity(Member sender, ChatRoom chatRoom) {
            return Chat.builder()
                    .sender(sender)
                    .chatRoom(chatRoom)
                    .message(message)
                    .type(type)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatResponseDto {

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
}
