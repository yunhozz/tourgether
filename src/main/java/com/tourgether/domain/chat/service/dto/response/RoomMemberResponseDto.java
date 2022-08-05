package com.tourgether.domain.chat.service.dto.response;

import com.tourgether.domain.chat.model.entity.RoomMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberResponseDto {

    private Long id;
    private Long userId;
    private Long chatRoomId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public RoomMemberResponseDto(RoomMember roomMember) {
        id = roomMember.getId();
        userId = roomMember.getMember().getId();
        chatRoomId = roomMember.getChatRoom().getId();
        createdDate = roomMember.getCreatedDate();
        lastModifiedDate = roomMember.getLastModifiedDate();
    }
}
