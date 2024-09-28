package com.azry.dmtp.z.config.handler.exception;

public class UnsupportedCurrencyException extends RuntimeException {

    public UnsupportedCurrencyException() {
        super("Currency is not supported");
    }

    public UnsupportedCurrencyException(String message) {
        super(message);
    }

    public UnsupportedCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
