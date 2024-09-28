package com.azry.dmtp.fiintegration.config.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnknownException extends RuntimeException {

    private final int statusCode;
    private final String fiIntegrationMessage;

}