package com.tourgether.domain.chat.service.dto.request;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ChatRequestDto {

    @NotNull
    private Member sender;

    @NotNull
    private ChatRoom chatRoom;

    @NotBlank
    private String message;

    @NotNull
    private MessageType type;

    @Builder
    private ChatRequestDto(Member sender, ChatRoom chatRoom, String message, MessageType type) {
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.message = message;
        this.type = type;
    }

    public Chat toEntity() {
        return Chat.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .message(message)
                .type(type)
                .build();
    }
}
