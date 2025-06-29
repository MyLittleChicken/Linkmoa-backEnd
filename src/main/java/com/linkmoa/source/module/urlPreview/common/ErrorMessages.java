package com.linkmoa.source.module.urlPreview.common;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    FETCH_FAILED("Failed to fetch"),
    NO_STRATEGY_FOUND("No strategy found");

    private final String message;

    ErrorMessages(final String message) {
        this.message = message;
    }
}