package com.tourgether.domain.chat.controller;

import com.tourgether.enums.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ChatForm {

    @NotBlank
    private String message;

    @NotNull
    private MessageType type;
}
