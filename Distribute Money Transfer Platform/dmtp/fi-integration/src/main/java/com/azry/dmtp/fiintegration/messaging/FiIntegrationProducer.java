package com.azry.dmtp.fiintegration.messaging;

import com.azry.dmtp.fiintegration.model.api.TransactionsProcessingCommand;
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
public class FiIntegrationProducer {

    private final KafkaTemplate<String, TransferStatusUpdateEvent> transferStatusTopicKafTemplate;
    private final KafkaTemplate<String, TransactionsProcessingCommand> bankTransactionStatusKafTemplate;

    public void sendTransferStatusMessage(TransferStatusUpdateEvent message) {
        String topic = Topics.TRANSFER_STATUS_TOPIC;
        KafkaMessageLogger.logMessageSendingAttempt(topic, message);

        CompletableFuture<SendResult<String, TransferStatusUpdateEvent>> result =
                transferStatusTopicKafTemplate.send(topic, message);

        result.whenComplete((sendResult, ex) -> {
            if (ex == null) {
                KafkaMessageLogger.logMessageSendingSuccess(topic, message);
            } else {
                KafkaMessageLogger.logMessageSendingFailure(topic, message, ex);
            }
        });
    }

    public void sendMessageToBankTransactionTopic(TransactionsProcessingCommand command) {
        String topic = KafkaTopic.BANK_SERVICE_TOPIC;
        bankTransactionStatusKafTemplate.send(topic, command);
    }
}
