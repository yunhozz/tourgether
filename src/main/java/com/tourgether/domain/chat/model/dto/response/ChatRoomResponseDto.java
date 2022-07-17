package com.tourgether.domain.chat.model.dto.response;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatRoomResponseDto {

    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        id = chatRoom.getId();
        title = chatRoom.getTitle();
        createdDate = chatRoom.getCreatedDate();
        lastModifiedDate = chatRoom.getLastModifiedDate();
    }
}
