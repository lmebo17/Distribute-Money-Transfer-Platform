package com.azry.dmtp.fiintegration.service;


import com.azry.dmtp.z.bankservice.TransactionCreationRequest;
import com.azry.dmtp.z.bankservice.TransactionDTO;

public interface MoneyTransactionService {

    TransactionDTO createTransaction(TransactionCreationRequest transactionCreationRequest);

    String getTransactionStatus(Long id);

}
