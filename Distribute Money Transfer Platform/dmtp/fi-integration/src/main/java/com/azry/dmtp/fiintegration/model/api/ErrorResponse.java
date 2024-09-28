package com.azry.dmtp.fiintegration.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String bankServiceMessage;
    private String fiIntegrationMessage;

    public static ErrorResponse errorResponse(int status, String fiIntegrationMessage, String bankServiceMessage) {
        return new ErrorResponse(status, fiIntegrationMessage, bankServiceMessage);
    }
}
