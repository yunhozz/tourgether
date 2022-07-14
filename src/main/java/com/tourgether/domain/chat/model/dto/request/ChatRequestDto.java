package com.tourgether.domain.chat.model.dto.request;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class ChatRequestDto {

    @Setter
    private Member sender;

    @Setter
    private ChatRoom chatRoom;

    @NotBlank
    private String message;

    public ChatRequestDto(String message) {
        this.message = message;
    }

    public Chat toEntity() {
        return Chat.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .message(message)
                .build();
    }
}
