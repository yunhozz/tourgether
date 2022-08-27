package com.tourgether.dto;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
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

        @NotBlank
        private String message;

        @NotNull
        private MessageType type;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomRequestDto {

        @NotBlank
        private String title;
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomResponseDto {

        private Long id;
        private Long userId;
        private String title;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public ChatRoomResponseDto(ChatRoom chatRoom) {
            id = chatRoom.getId();
            userId = chatRoom.getMember().getId();
            title = chatRoom.getTitle();
            createdDate = chatRoom.getCreatedDate();
            lastModifiedDate = chatRoom.getLastModifiedDate();
        }
    }
}
