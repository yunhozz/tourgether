package com.tourgether.domain.chat.model.dto.request;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ChatRequestDto {

    @Setter
    private Member sender;

    @Setter
    private ChatRoom chatRoom;

    @NotNull
    private MessageType type;

    @NotBlank
    private String message;

    public ChatRequestDto(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public Chat toEntity() {
        return Chat.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .type(type)
                .message(message)
                .build();
    }
}
