package com.azry.dmtp.transferrepository.messaging;

import com.azry.dmtp.logging.KafkaMessageLogger;
import com.azry.dmtp.messaging.Topics;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
public class TransferRepositoryProducer {

    private final KafkaTemplate<String, TransferStatusUpdateEvent> kafkaTemplate;

    public void sendMessage(TransferStatusUpdateEvent message) {
        String topic = Topics.TRANSFER_STATUS_TOPIC;
        KafkaMessageLogger.logMessageSendingAttempt(topic, message);

        ListenableFuture<SendResult<String, TransferStatusUpdateEvent>> result =
                kafkaTemplate.send(topic, message);

        result.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(@NonNull Throwable ex) {
                KafkaMessageLogger.logMessageSendingFailure(topic, message, ex);
            }

            @Override
            public void onSuccess(SendResult<String, TransferStatusUpdateEvent> sendResult) {
                KafkaMessageLogger.logMessageSendingSuccess(topic, message);
            }
        });

    }
}