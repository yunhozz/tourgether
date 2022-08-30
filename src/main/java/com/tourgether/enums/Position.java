package com.tourgether.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Position {

    LEADER("TEAM LEADER", "팀장"),
    MEMBER("TEAM MEMBER", "팀원");

    private final String key;
    private final String value;
}
