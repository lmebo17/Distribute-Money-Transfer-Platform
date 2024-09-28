package com.azry.dmtp.fiintegration.config.handler.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MethodArgumentNotValidException extends RuntimeException {

    private final int statusCode;
    private final String fiIntegrationMessage;
}
