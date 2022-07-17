package com.tourgether.domain.chat.model.dto.response;

import com.tourgether.domain.chat.model.entity.ChatRoomMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatRoomMemberResponseDto {

    private Long id;
    private Long userId;
    private Long chatRoomId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public ChatRoomMemberResponseDto(ChatRoomMember chatRoomMember) {
        id = chatRoomMember.getId();
        userId = chatRoomMember.getMember().getId();
        chatRoomId = chatRoomMember.getChatRoom().getId();
        createdDate = chatRoomMember.getCreatedDate();
        lastModifiedDate = chatRoomMember.getLastModifiedDate();
    }
}
