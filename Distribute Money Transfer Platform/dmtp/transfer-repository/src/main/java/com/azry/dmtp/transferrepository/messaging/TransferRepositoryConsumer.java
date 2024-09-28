package com.azry.dmtp.transferrepository.messaging;

import com.azry.dmtp.logging.KafkaMessageLogger;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.transferrepository.model.kafka.KafkaObject;
import com.azry.dmtp.transferrepository.model.kafka.KafkaTopics;
import com.azry.dmtp.transferrepository.service.TransferStatusTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferRepositoryConsumer {

    private final TransferStatusTopicService transferStatusTopicService;

    @KafkaListener(topics = KafkaTopics.TRANSFER_REPOSITORY_TEST_TOPIC)
    public void listen(KafkaObject message) {
        System.out.println("Received message: " + message);
    }

    @KafkaListener(topics = Topics.TRANSFER_STATUS_TOPIC)
    public void listenToTransferStatusTopic(TransferStatusUpdateEvent message) {
        String topic = Topics.TRANSFER_STATUS_TOPIC;
        KafkaMessageLogger.logReceivedMessage(topic, message);
        KafkaMessageLogger.logMessageProcessing();
        transferStatusTopicService.updateDatabase(message);
        KafkaMessageLogger.logMessageProcessingSuccess();
    }
}