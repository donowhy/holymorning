package com.ssafy.crit.users.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    Begginer("0", "초보자"),
    IntermediateLow("150", "수습 챌린저"),
    IntermediateMiddle("300","중견 챌린저"),
    IntermediateHigh("500", "상급 챌린저"),
    Challenger("1000", "신");

    private final String key;
    private final String title;
}