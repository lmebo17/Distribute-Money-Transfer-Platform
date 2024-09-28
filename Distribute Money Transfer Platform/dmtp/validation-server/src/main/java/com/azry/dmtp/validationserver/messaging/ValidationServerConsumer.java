package com.azry.dmtp.validationserver.messaging;

import com.azry.dmtp.logging.KafkaMessageLogger;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.validationserver.service.ValidationServerConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidationServerConsumer {

    private final ValidationServerConsumerService validationServerConsumerService;

    @KafkaListener(topicPattern = Topics.VALIDATION_SERVER_TOPIC)
    public void validateTransferStatusUpdateEvent(TransferStatusUpdateEvent message) {
        String topic = Topics.VALIDATION_SERVER_TOPIC;
        KafkaMessageLogger.logReceivedMessage(topic, message);
        KafkaMessageLogger.logMessageProcessing();

        validationServerConsumerService.validateTransferStatusUpdateEvent(message);

        KafkaMessageLogger.logMessageProcessingSuccess();
    }

}
