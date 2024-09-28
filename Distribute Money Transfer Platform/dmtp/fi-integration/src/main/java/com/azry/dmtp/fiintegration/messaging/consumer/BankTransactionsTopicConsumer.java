package com.azry.dmtp.fiintegration.messaging.consumer;

import com.azry.dmtp.fiintegration.config.BankServiceProperties;
import com.azry.dmtp.fiintegration.config.SchedulerConfig;
import com.azry.dmtp.fiintegration.mapper.MoneyTransactionMapper;
import com.azry.dmtp.fiintegration.mapper.TransactionProcessingCommandMapper;
import com.azry.dmtp.fiintegration.messaging.FiIntegrationProducer;
import com.azry.dmtp.fiintegration.messaging.KafkaTopic;
import com.azry.dmtp.fiintegration.model.api.TransactionsProcessingCommand;
import com.azry.dmtp.fiintegration.model.api.dto.MoneyTransactionDTO;
import com.azry.dmtp.fiintegration.model.enums.TransactionStatus;
import com.azry.dmtp.fiintegration.service.implementation.BankClientServiceImpl;
import com.azry.dmtp.messaging.StatusMessages;
import com.azry.dmtp.model.TransferStatusInfo;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.z.bankservice.TransactionDTO;
import com.azry.dmtp.z.bankservice.api.TransactionsApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class BankTransactionsTopicConsumer {
    private static final Set<TransactionStatus> STATUS_TO_REPROCESS =
            Set.of(TransactionStatus.CREATED, TransactionStatus.PENDING);
    private final FiIntegrationProducer producer;
    private final BankServiceProperties bankServiceProperties;
    private final BankClientServiceImpl bankClientService;
    private final SchedulerConfig schedulerConfig;
    private final TransactionProcessingCommandMapper mapper;
    private final MoneyTransactionMapper moneyTransactionMapper;
    private final TransactionsApi transactionsApi;

    private static TransferStatusInfo buildTransferStatusInfo(TransferStatusModel statusModel) {
        TransferStatusInfo eventStatus = new TransferStatusInfo();
        eventStatus.setStatusTime(LocalDateTime.now());
        eventStatus.setStatus(statusModel);
        eventStatus.setStatusMessage(StatusMessages.statusMessages.get(eventStatus.getStatus()));
        return eventStatus;
    }

    @KafkaListener(topics = KafkaTopic.BANK_SERVICE_TOPIC)
    public void listenToBankTransactionTopic(TransactionsProcessingCommand command) {
        if (command.getBankStatus() == null || command.getBankStatus() == TransactionStatus.REJECTED) {
            MoneyTransactionDTO transactionDTO = bankClientService.sendCommandToBank(command);
            Long transactionId = transactionDTO.getId();
            checkTransaction(command, transactionId);
        } else {
            log.warn("Incorrect bank status of command");
        }
    }

    @Async
    protected void checkTransaction(TransactionsProcessingCommand command, Long transactionId) {
        System.out.println(Thread.currentThread().getName() + ": Checking transaction " + transactionId);
        MoneyTransactionDTO transaction = getMoneyTransactionDTO(transactionId);
        log.info("Checking transaction status: {}", transaction.getStatus());
        command.setBankStatus(transaction.getStatus());

        if (command.getBankStatus() == TransactionStatus.PROCESSED) {
            processSuccessfulTransactions(command);
        } else if (STATUS_TO_REPROCESS.contains(command.getBankStatus())) {
            scheduleReprocessing(command, transactionId);
        } else if (command.getBankStatus() == TransactionStatus.REJECTED) {
            reprocessRejectedTransaction(command);
        }
    }

    @Async
    protected void processSuccessfulTransactions(TransactionsProcessingCommand command) {
        log.info("Transaction processed successfully for transferId: {}", command.getTransfer().getId());
        TransferStatusModel newStatus = (command.getTransferStatusModel() == TransferStatusModel.SEND_MONEY_COLLECTION_REQUESTED)
                ? TransferStatusModel.SEND_MONEY_COLLECTED
                : TransferStatusModel.RECEIVE_PAID;
        TransferStatusUpdateEvent event = commandToEvent(command, newStatus);
        producer.sendTransferStatusMessage(event);
    }

    private void reprocessRejectedTransaction(TransactionsProcessingCommand command) {
        if (command.getRetryCount() < bankServiceProperties.getBankServiceRetryCount()) {
            log.warn("Transaction rejected for transferId: {}. RetryCount: {}",
                    command.getTransfer().getId(), command.getRetryCount());
            sendRetry(command);
        } else {
            processRejectedTransaction(command);
        }
    }

    private void processRejectedTransaction(TransactionsProcessingCommand command) {
        TransferStatusModel newStatus = (command.getTransferStatusModel() == TransferStatusModel.SEND_MONEY_COLLECTION_REQUESTED)
                ? TransferStatusModel.SEND_MONEY_COLLECTION_REJECTED
                : TransferStatusModel.RECEIVE_PAY_REJECTED;
        TransferStatusUpdateEvent event = commandToEvent(command, newStatus);
        producer.sendTransferStatusMessage(event);
    }

    private void scheduleReprocessing(TransactionsProcessingCommand command, Long transactionId) {
        try {
            Thread.sleep(schedulerConfig.getReprocessingDelayInMillis());
            checkTransaction(command, transactionId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Reprocessing was interrupted", e);
        }
    }

    private void sendRetry(TransactionsProcessingCommand command) {
        command.setRetryCount(command.getRetryCount() + 1);
        producer.sendMessageToBankTransactionTopic(command);
    }

    private TransferStatusUpdateEvent commandToEvent(TransactionsProcessingCommand command, TransferStatusModel statusModel) {
        TransferStatusUpdateEvent event = mapper.toTransferStatusUpdateEvent(command);
        TransferStatusInfo eventStatus = buildTransferStatusInfo(statusModel);
        event.getTransfer().setStatus(eventStatus);
        event.setStatus(eventStatus);
        return event;
    }

    private MoneyTransactionDTO getMoneyTransactionDTO(Long transactionId) {
        TransactionDTO transaction = transactionsApi.getTransaction(transactionId);
        return moneyTransactionMapper.transactionDtoToMoneyTransactionDto(transaction);
    }
}
