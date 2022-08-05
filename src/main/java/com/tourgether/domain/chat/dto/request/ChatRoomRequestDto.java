package com.tourgether.domain.chat.dto.request;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRequestDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String title;

    public ChatRoom toEntity(Member member) {
        return new ChatRoom(member, title);
    }
}
