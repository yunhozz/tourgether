package com.tourgether.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // global
    NOT_FOUND(404, "COMMON-ERR-404", "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "COMMON-ERR-500", "INTER SERVER ERROR"),

    // member
    MEMBER_NOT_FOUND(400, "MEMBER-ERR-400", "회원정보를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(400, "MEMBER-ERR-400", "이메일을 찾을 수 없습니다."),
    EMAIL_DUPLICATION(400, "MEMBER-ERR-400", "중복된 이메일이 존재합니다."),
    NICKNAME_DUPLICATION(400, "MEMBER-ERR-400", "중복된 닉네임이 존재합니다."),
    PASSWORD_MISMATCH(400, "MEMBER-ERR-400", "패스워드가 일치하지 않습니다."),
    PASSWORD_SAME(400, "MEMBER-ERR-400", "기존 비밀번호와 일치합니다."),

    // notification
    NOTIFICATION_NOT_FOUND(400, "NOTIFICATION-ERR-400", "알림을 찾을 수 없습니다."),

    // recruit
    RECRUIT_NOT_FOUND(400, "RECRUIT-ERR-400", "모집글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(400, "COMMENT-ERR-400", "댓글을 찾을 수 없습니다."),
    WRITER_MISMATCH(400, "RECRUIT-ERR-400", "수정 권한이 없습니다."),
    KEYWORD_NOT_ENTERED(400, "RECRUIT-ERR-400", "키워드를 입력해주세요."),

    // chat
    CHAT_NOT_FOUND(400, "CHATTING-ERR-400", "채팅 내역을 찾을 수 없습니다."),
    CHATROOM_NOT_FOUND(400, "CHATROOM-ERR-400", "채팅방이 존재하지 않습니다."),

    // bookmark
    BOOKMARK_NOT_FOUND(400, "BOOKMARK-ERR-400", "북마크를 찾을 수 없습니다."),

    // apply
    APPLY_NOT_FOUND(400, "APPLY-ERR-400", "지원 내역을 찾을 수 없습니다."),
    ALREADY_APPLY(400, "APPLY-ERR-400", "이미 지원한 모집글입니다."),
    ALREADY_DECIDED(400, "APPLY-ERR-400", "이미 지원이 만료되었습니다."),
    ACCEPTANCE_NOT_INSERTED(400, "APPLY-ERR-400", "지원 수락, 거절 여부를 입력해주세요.");

    private final int status;
    private final String code;
    private final String message;
}
