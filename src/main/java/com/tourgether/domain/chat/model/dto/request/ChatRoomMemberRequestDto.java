package com.tourgether.domain.chat.model.dto.request;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.chat.model.entity.ChatRoomMember;
import com.tourgether.domain.member.model.Member;
import lombok.Getter;

@Getter
public class ChatRoomMemberRequestDto {

    private Member member;
    private ChatRoom chatRoom;

    public ChatRoomMemberRequestDto(Member member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public ChatRoomMember toEntity() {
        return new ChatRoomMember(member, chatRoom);
    }
}
