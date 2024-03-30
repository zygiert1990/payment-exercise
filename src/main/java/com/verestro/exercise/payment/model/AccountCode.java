package com.verestro.exercise.payment.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AccountCode {
    CODE_1(100),
    CODE_2(200),
    CODE_3(300),
    CODE_4(400),
    CODE_5(500);

    private final int value;

}
