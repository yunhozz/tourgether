package com.tourgether.domain.chat.service.dto.request;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {

    @NotNull
    private Member member;

    @NotBlank
    private String title;

    public ChatRoomRequestDto(Member member, String title) {
        this.member = member;
        this.title = title;
    }

    public ChatRoom toEntity() {
        return new ChatRoom(member, title);
    }
}
