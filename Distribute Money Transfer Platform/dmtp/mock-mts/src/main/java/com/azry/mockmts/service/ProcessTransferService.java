package com.azry.mockmts.service;

import com.azry.dmtp.messaging.StatusMessages;
import com.azry.dmtp.model.TransferStatusInfo;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.mockmts.messaging.MtsTopicProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessTransferService {

    private final MtsTopicProducer producer;

    public void validateTransfer(TransferStatusUpdateEvent message) {
        log.info("Received Message in mts-topic: {}", message);
        TransferStatusInfo status = message.getStatus();

        if (status.getStatus() != TransferStatusModel.SEND_REQUESTED && status.getStatus() != TransferStatusModel.RECEIVE_REQUESTED)
            return;

        if (status.getStatus() == TransferStatusModel.SEND_REQUESTED) {
            status.setStatus(TransferStatusModel.SEND_SENT);
        } else {
            status.setStatus(TransferStatusModel.RECEIVE_RECEIVED);
        }

        status.setStatusMessage(StatusMessages.statusMessages.get(status.getStatus()));
        status.setStatusTime(LocalDateTime.now());
        message.setStatus(status);
        message.getTransfer().setStatus(status);
        producer.sendMessage(message);
    }
}
