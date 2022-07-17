package com.tourgether.domain.chat.model.dto.request;

import com.tourgether.domain.chat.model.entity.ChatRoom;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ChatRoomRequestDto {

    @NotBlank
    private String title;

    public ChatRoomRequestDto(String title) {
        this.title = title;
    }

    public ChatRoom toEntity() {
        return new ChatRoom(title);
    }
}
