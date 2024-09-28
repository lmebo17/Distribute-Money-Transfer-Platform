package com.azry.dmtp.fiintegration.messaging.consumer;

import com.azry.dmtp.fiintegration.service.BankTransactionTopicService;
import com.azry.dmtp.logging.KafkaMessageLogger;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FiIntegrationTopicConsumer {

    private final BankTransactionTopicService bankTransactionTopicService;

    @KafkaListener(topicPattern = Topics.FI_INTEGRATION_TOPIC)
    public void listen(TransferStatusUpdateEvent message) {
        String topic = Topics.FI_INTEGRATION_TOPIC;
        KafkaMessageLogger.logReceivedMessage(topic, message);
        KafkaMessageLogger.logMessageProcessing();

        bankTransactionTopicService.sendCommandToBankTransactionTopic(message);

        KafkaMessageLogger.logMessageProcessingSuccess();
    }
}
