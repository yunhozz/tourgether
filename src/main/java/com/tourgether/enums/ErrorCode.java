package com.tourgether.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND(404, "COMMON-ERR-404", "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "COMMON-ERR-500", "INTER SERVER ERROR"),

    // member
    MEMBER_NOT_FOUND(400, "MEMBER-ERR-400", "MEMBER NOT FOUND"),
    EMAIL_DUPLICATION(400, "MEMBER-ERR-400", "EMAIL DUPLICATED"),
    NICKNAME_DUPLICATION(400, "MEMBER-ERR-400", "NICKNAME DUPLICATED"),
    PASSWORD_MISMATCH(400, "MEMBER-ERR-400", "PASSWORD DO NOT MATCH"),

    // notification
    NOTIFICATION_NOT_FOUND(400, "NOTIFICATION-ERR-400", "NOTIFICATION NOT FOUND"),

    // recruit
    RECRUIT_NOT_FOUND(400, "RECRUIT-ERR-400", "RECRUITMENT NOT FOUND"),
    WRITER_MISMATCH(400, "RECRUIT-ERR-400", "WRITER DO NOT MATCH"),

    // chat
    CHAT_NOT_FOUND(400, "CHATTING-ERR-400", "CHAT NOT FOUND"),
    CHATROOM_NOT_FOUND(400, "CHATROOM-ERR-400", "CHATROOM NOT FOUND");

    private final int status;
    private final String code;
    private final String message;
}
