package com.tourgether.domain.chat.dto.request;

import com.tourgether.domain.chat.model.entity.Chat;
import com.tourgether.domain.chat.model.entity.ChatRoom;
import com.tourgether.domain.member.model.entity.Member;
import com.tourgether.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {

    @NotBlank
    private String message;

    @NotNull
    private MessageType type;

    public Chat toEntity(Member sender, ChatRoom chatRoom) {
        return Chat.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .message(message)
                .type(type)
                .build();
    }
}
