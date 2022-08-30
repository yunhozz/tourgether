package com.tourgether.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchCondition {

    LATEST("SEQ_LATEST", "최신순"),
    MODIFIED("SEQ_MODIFIED", "수정 순"),
    POPULARITY("SEQ_POPULARITY", "인기 순"),
    ACCURACY("SEQ_ACCURACY", "정확도순");

    private final String key;
    private final String value;
}
