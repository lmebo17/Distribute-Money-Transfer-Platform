package com.azry.mockmts.messaging;

import com.azry.dmtp.logging.KafkaMessageLogger;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.mockmts.service.ProcessTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MtsTopicConsumer {

    private final ProcessTransferService validationService;

    @KafkaListener(topicPattern = Topics.MTS_TOPIC)
    public void listenToMtsTopic(TransferStatusUpdateEvent message) {
        String topic = Topics.MTS_TOPIC;
        KafkaMessageLogger.logReceivedMessage(topic, message);
        KafkaMessageLogger.logMessageProcessing();

        validationService.validateTransfer(message);

        KafkaMessageLogger.logMessageProcessingSuccess();
    }
}
