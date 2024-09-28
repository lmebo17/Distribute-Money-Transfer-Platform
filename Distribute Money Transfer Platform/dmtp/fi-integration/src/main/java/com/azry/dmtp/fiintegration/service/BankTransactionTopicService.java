package com.azry.dmtp.fiintegration.service;

import com.azry.dmtp.model.TransferStatusUpdateEvent;

public interface BankTransactionTopicService {
    void sendCommandToBankTransactionTopic(TransferStatusUpdateEvent message);
}
