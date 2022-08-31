package com.tourgether.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    OFFER("TYPE_OFFER", "참가 제안 알림"),
    RESPONSE("TYPE_RESPONSE", "제안 답장"),
    CHAT("TYPE_CHAT", "채팅 알림"),
    BOOKMARK("TYPE_BOOKMARK", "북마크 알림"),
    COMMENT("TYPE_COMMENT", "댓글 알림");

    private final String key;
    private final String value;
}
