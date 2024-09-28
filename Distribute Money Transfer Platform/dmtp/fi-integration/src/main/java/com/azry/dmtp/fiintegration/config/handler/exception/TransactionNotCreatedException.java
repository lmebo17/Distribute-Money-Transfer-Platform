package com.azry.dmtp.fiintegration.config.handler.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransactionNotCreatedException extends RuntimeException {

    private final int statusCode;
    private final String fiIntegrationMessage;
    private final String bankServiceMessage;
}
