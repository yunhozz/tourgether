package com.tourgether.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {

    JOIN("CHAT_ENTER", "입장 메세지"),
    CHAT("CHAT_TALK", "채팅 메세지");

    private final String key;
    private final String value;
}
