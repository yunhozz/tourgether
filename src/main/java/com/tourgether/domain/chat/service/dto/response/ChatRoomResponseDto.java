package com.tourgether.domain.chat.service.dto.response;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDto {

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
