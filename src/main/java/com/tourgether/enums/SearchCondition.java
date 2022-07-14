package com.tourgether.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchCondition {

    LATEST_ORDER("SEQ_LATEST", "최신순"),
    ACCURACY_ORDER("SEQ_ACCURACY", "정확도순");

    private final String key;
    private final String value;
}
