package com.azry.dmtp.fiintegration.config.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BadRequestException extends RuntimeException {

    private final int statusCode;
    private final String fiIntegrationMessage;
    private final String bankServiceMessage;
}