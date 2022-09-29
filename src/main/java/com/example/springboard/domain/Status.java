package com.example.springboard.domain;

import lombok.Getter;

import java.text.Normalizer;

@Getter
public enum Status {

    BANNED("STATUS_BANNED"),
    NORMAL("STATUS_NORMAL");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
