package com.azry.dmtp.fiintegration.service;

import com.azry.dmtp.fiintegration.model.api.TransactionsProcessingCommand;
import com.azry.dmtp.fiintegration.model.api.dto.MoneyTransactionDTO;

public interface BankClientService {
    MoneyTransactionDTO sendCommandToBank(TransactionsProcessingCommand transferCommand);
}
