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
    PASSWORD_MISMATCH(400, "MEMBER-ERR-400", "PASSWORD DO NOT MATCH"),

    // notification
    NOTIFICATION_NOT_FOUND(400, "NOTIFICATION-ERR-400", "NOTIFICATION NOT FOUND");

    private final int status;
    private final String code;
    private final String message;
}
