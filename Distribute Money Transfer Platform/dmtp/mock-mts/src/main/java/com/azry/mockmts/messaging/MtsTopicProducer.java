package com.azry.mockmts.messaging;

import com.azry.dmtp.logging.KafkaMessageLogger;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class MtsTopicProducer {
    private final KafkaTemplate<String, TransferStatusUpdateEvent> kafkaTemplate;

    public void sendMessage(TransferStatusUpdateEvent message) {
        String topic = Topics.TRANSFER_STATUS_TOPIC;
        KafkaMessageLogger.logMessageSendingAttempt(topic, message);

        CompletableFuture<SendResult<String, TransferStatusUpdateEvent>> result =
                kafkaTemplate.send(topic, message);

        result.whenComplete((sendResult, ex) -> {
            if (ex == null) {
                KafkaMessageLogger.logMessageSendingSuccess(topic, message);
            } else {
                KafkaMessageLogger.logMessageSendingFailure(topic, message, ex);
            }
        });
    }
}
