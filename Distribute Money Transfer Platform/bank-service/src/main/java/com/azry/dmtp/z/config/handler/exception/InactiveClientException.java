package com.azry.dmtp.z.config.handler.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InactiveClientException extends RuntimeException {
    public InactiveClientException(Long personalNumber) {
        super("Client with personal number " + personalNumber + " is inactive. Activate the client first.");
    }

    public InactiveClientException(String personalNumber, Throwable cause) {
        super("Client with personal number " + personalNumber + " is inactive. Activate the client first.");
    }

    public InactiveClientException(Throwable cause) {
        super(cause);
    }
}
