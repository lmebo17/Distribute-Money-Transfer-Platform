package com.azry.dmtp.validationserver.service;

import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.validationserver.model.ValidationResult;

import java.util.Optional;

public abstract class AbstractValidationProcessor {

    private AbstractValidationProcessor nextProcessor;

    public AbstractValidationProcessor addProcessor(AbstractValidationProcessor processor) {
        this.nextProcessor = processor;
        return nextProcessor;
    }

    public void processValidation(TransferModel transfer, ValidationResult result) {
        process(transfer, result);
        Optional.ofNullable(nextProcessor)
                .ifPresent(processor -> processor.processValidation(transfer, result));
    }

    public abstract void process(TransferModel transfer, ValidationResult result);
}
