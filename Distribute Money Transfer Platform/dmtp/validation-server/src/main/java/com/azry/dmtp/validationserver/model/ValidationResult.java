package com.azry.dmtp.validationserver.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ValidationResult {
    private boolean isTransferValid;
    private List<String> messages;
}
