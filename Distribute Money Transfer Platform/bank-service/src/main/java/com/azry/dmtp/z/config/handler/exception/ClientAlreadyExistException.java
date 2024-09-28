package com.azry.dmtp.z.config.handler.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClientAlreadyExistException extends RuntimeException {
    public ClientAlreadyExistException(Long personalNumber) {
        super("Client with personal number " + personalNumber + " already exists.");
    }

    public ClientAlreadyExistException(String personalNumber, Throwable cause) {
        super("Client with personal number " + personalNumber + " already exists.", cause);
    }

    public ClientAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
