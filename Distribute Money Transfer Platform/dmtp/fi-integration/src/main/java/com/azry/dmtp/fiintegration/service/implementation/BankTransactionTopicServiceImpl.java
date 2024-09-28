package com.azry.dmtp.fiintegration.service.implementation;

import com.azry.dmtp.fiintegration.mapper.TransactionProcessingCommandMapper;
import com.azry.dmtp.fiintegration.messaging.FiIntegrationProducer;
import com.azry.dmtp.fiintegration.model.api.TransactionsProcessingCommand;
import com.azry.dmtp.fiintegration.service.BankTransactionTopicService;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BankTransactionTopicServiceImpl implements BankTransactionTopicService {

    private static final Set<TransferStatusModel> STATUSES_TO_PROCESS =
            Set.of(TransferStatusModel.SEND_MONEY_COLLECTION_REQUESTED, TransferStatusModel.RECEIVE_PAY_REQUESTED);
    private final FiIntegrationProducer bankTransactionTopicProducer;
    private final TransactionProcessingCommandMapper mapper;

    public void sendCommandToBankTransactionTopic(TransferStatusUpdateEvent message) {
        TransferStatusModel status = message.getStatus().getStatus();
        if (STATUSES_TO_PROCESS.contains(status)) {
            TransactionsProcessingCommand command = mapper.toTransactionProcessingCommand(message);
            command.setTransferStatusModel(status);
            bankTransactionTopicProducer.sendMessageToBankTransactionTopic(command);
        }
    }
}
