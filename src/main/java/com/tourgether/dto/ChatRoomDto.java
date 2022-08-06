package com.tourgether.dto;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ChatRoomDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomRequestDto {

        @NotNull
        private Long userId;

        @NotBlank
        private String title;

        public ChatRoom toEntity(Member member) {
            return new ChatRoom(member, title);
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
