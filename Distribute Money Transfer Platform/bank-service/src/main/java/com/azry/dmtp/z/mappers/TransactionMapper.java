package com.azry.dmtp.z.mappers;

import com.azry.dmtp.z.model.api.TransactionCreationRequest;
import com.azry.dmtp.z.model.api.dto.TransactionDTO;
import com.azry.dmtp.z.model.entity.Transaction;
import com.azry.dmtp.z.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionMapper {

    private AccountService accountService;

    public Transaction toTransaction(TransactionCreationRequest transactionCreationRequest) {
        if (transactionCreationRequest == null) {
            return null;
        }

        return Transaction.builder()
                .senderAccount(accountService.getAccount(transactionCreationRequest.getSenderAccountNumber()))
                .receiverAccount(accountService.getAccount(transactionCreationRequest.getReceiverAccountNumber()))
                .amount(transactionCreationRequest.getAmount())
                .currency(transactionCreationRequest.getCurrency())
                .build();
    }

    public TransactionDTO toTransactionDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionDTO.builder()
                .id(transaction.getTransactionId())
                .receiverAccountNumber(transaction.getReceiverAccount().getAccountNumber())
                .senderAccountNumber(transaction.getSenderAccount().getAccountNumber())
                .status(transaction.getStatus())
                .creationDate(transaction.getCreationDate())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .build();
    }
}
