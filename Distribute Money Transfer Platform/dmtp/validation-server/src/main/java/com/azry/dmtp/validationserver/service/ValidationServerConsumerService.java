package com.azry.dmtp.validationserver.service;

import com.azry.dmtp.messaging.StatusMessages;
import com.azry.dmtp.model.TransferStatusInfo;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.validationserver.messaging.ValidationServerProducer;
import com.azry.dmtp.validationserver.model.ValidationResult;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ValidationServerConsumerService {

    private final ValidationServerProducer producer;
    private final ParticipantValidator participantValidator;
    private final LimitsValidator limitsValidator;

    @PostConstruct
    private void init() {
        participantValidator.addProcessor(limitsValidator);
    }

    public void validateTransferStatusUpdateEvent(TransferStatusUpdateEvent message) {
        TransferStatusInfo statusInfo = message.getStatus();
        TransferStatusModel status = statusInfo.getStatus();
        if (status != TransferStatusModel.SEND_VALIDATION_REQUESTED && status != TransferStatusModel.RECEIVE_VALIDATION_REQUESTED) {
            return;
        }

        ValidationResult res = ValidationResult.builder().isTransferValid(true).messages(new ArrayList<>()).build();
        participantValidator.processValidation(message.getTransfer(), res);

        if (status == TransferStatusModel.SEND_VALIDATION_REQUESTED) {
            statusInfo.setStatus(res.isTransferValid() ? TransferStatusModel.SEND_VALIDATED : TransferStatusModel.SEND_VALIDATION_REJECTED);
        } else {
            statusInfo.setStatus(res.isTransferValid() ? TransferStatusModel.RECEIVE_VALIDATED : TransferStatusModel.RECEIVE_VALIDATION_REJECTED);
        }

        statusInfo.setStatusTime(LocalDateTime.now());
        statusInfo.setStatusMessage(StatusMessages.statusMessages.get(statusInfo.getStatus()));
        message.setStatus(statusInfo);
        message.getTransfer().setStatus(message.getStatus());

        producer.sendMessage(message);
    }

}
