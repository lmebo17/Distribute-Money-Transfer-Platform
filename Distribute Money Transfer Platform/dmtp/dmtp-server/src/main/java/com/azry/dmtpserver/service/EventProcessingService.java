package com.azry.dmtpserver.service;

import com.azry.dmtp.messaging.StatusMessages;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtpserver.messaging.DmtpServerProducer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventProcessingService {

    private final DmtpServerProducer kafkaProducer;
    private Map<TransferStatusModel, Consumer<TransferStatusUpdateEvent>> statusActions;

    @PostConstruct
    public void init() {
        statusActions = Map.ofEntries(
                Map.entry(TransferStatusModel.SEND_CREATED, this::SendCreatedTransfer),
                Map.entry(TransferStatusModel.SEND_VALIDATION_REQUESTED, kafkaProducer::sendToTransferValidationTopic),
                Map.entry(TransferStatusModel.SEND_VALIDATED, event -> transferStatusUpdate(event, TransferStatusModel.SEND_MONEY_COLLECTION_REQUESTED)),
                Map.entry(TransferStatusModel.SEND_VALIDATION_REJECTED, event -> logTransferFinalState(TransferStatusModel.SEND_VALIDATION_REJECTED)),
                Map.entry(TransferStatusModel.SEND_MONEY_COLLECTION_REQUESTED, kafkaProducer::sendToFiIntegrationTopic),
                Map.entry(TransferStatusModel.SEND_MONEY_COLLECTED, event -> transferStatusUpdate(event, TransferStatusModel.SEND_REQUESTED)),
                Map.entry(TransferStatusModel.SEND_MONEY_COLLECTION_REJECTED, event -> logTransferFinalState(TransferStatusModel.SEND_MONEY_COLLECTION_REJECTED)),
                Map.entry(TransferStatusModel.SEND_REQUESTED, kafkaProducer::sendToMtsTopic),
                Map.entry(TransferStatusModel.SEND_SENT, event -> logTransferFinalState(TransferStatusModel.SEND_SENT)),
                Map.entry(TransferStatusModel.SEND_REJECTED, event -> logTransferFinalState(TransferStatusModel.SEND_REJECTED)),
                Map.entry(TransferStatusModel.RECEIVE_CREATED, event -> transferStatusUpdate(event, TransferStatusModel.RECEIVE_VALIDATION_REQUESTED)),
                Map.entry(TransferStatusModel.RECEIVE_VALIDATION_REQUESTED, kafkaProducer::sendToTransferValidationTopic),
                Map.entry(TransferStatusModel.RECEIVE_VALIDATED, event -> transferStatusUpdate(event, TransferStatusModel.RECEIVE_REQUESTED)),
                Map.entry(TransferStatusModel.RECEIVE_VALIDATION_REJECTED, event -> logTransferFinalState(TransferStatusModel.RECEIVE_VALIDATION_REJECTED)),
                Map.entry(TransferStatusModel.RECEIVE_REQUESTED, kafkaProducer::sendToMtsTopic),
                Map.entry(TransferStatusModel.RECEIVE_RECEIVED, event -> transferStatusUpdate(event, TransferStatusModel.RECEIVE_PAY_REQUESTED)),
                Map.entry(TransferStatusModel.RECEIVE_REJECTED, event -> logTransferFinalState(TransferStatusModel.RECEIVE_REJECTED)),
                Map.entry(TransferStatusModel.RECEIVE_PAY_REQUESTED, kafkaProducer::sendToFiIntegrationTopic),
                Map.entry(TransferStatusModel.RECEIVE_PAID, event -> logTransferFinalState(TransferStatusModel.RECEIVE_PAID)),
                Map.entry(TransferStatusModel.RECEIVE_PAY_REJECTED, event -> logTransferFinalState(TransferStatusModel.RECEIVE_PAY_REJECTED))
        );
    }

    @KafkaListener(topics = Topics.TRANSFER_STATUS_TOPIC)
    public void listenTransferStatus(TransferStatusUpdateEvent event) {
        TransferStatusModel transferStatus = event.getStatus().getStatus();
        Consumer<TransferStatusUpdateEvent> status = statusActions.get(transferStatus);
        if (status != null) {
            status.accept(event);
        } else {
            log.warn("Unknown transfer status: {}", transferStatus);
        }
    }

    private void SendCreatedTransfer(TransferStatusUpdateEvent event) {
        updateTransferStatus(event, TransferStatusModel.SEND_VALIDATION_REQUESTED);
        kafkaProducer.sendToTransferStatusTopic(event);
    }

    private void transferStatusUpdate(TransferStatusUpdateEvent event, TransferStatusModel newStatus) {
        updateTransferStatus(event, newStatus);
        kafkaProducer.sendToTransferStatusTopic(event);
    }

    private void updateTransferStatus(TransferStatusUpdateEvent event, TransferStatusModel newStatus) {
        event.getStatus().setStatus(newStatus);
        event.getStatus().setStatusTime(LocalDateTime.now());
        event.getStatus().setStatusMessage(StatusMessages.statusMessages.get(newStatus));
        event.getTransfer().setStatus(event.getStatus());
    }

    private void logTransferFinalState(TransferStatusModel status) {
        log.info(StatusMessages.statusMessages.get(status));
    }
}