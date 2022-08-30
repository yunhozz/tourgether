package com.tourgether.dto;

import com.tourgether.domain.chat.model.entity.Chatroom;
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
        private Long chatroomId;

        @NotNull
        private String writer;

        @NotBlank(message = "메세지를 입력해주세요.")
        private String message;

        @NotNull
        private MessageType type;

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatroomRequestDto {

        @NotBlank(message = "제목을 입력해주세요.")
        private String title;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatroomResponseDto {

        private Long id;
        private Long userId;
        private String title;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public ChatroomResponseDto(Chatroom chatroom) {
            id = chatroom.getId();
            userId = chatroom.getMember().getId();
            title = chatroom.getTitle();
            createdDate = chatroom.getCreatedDate();
            lastModifiedDate = chatroom.getLastModifiedDate();
        }
    }
}
